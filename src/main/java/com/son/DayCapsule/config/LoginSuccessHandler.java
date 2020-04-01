package com.son.DayCapsule.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 로그인 성공 시
 * 로그인 이전에 있었던 화면으로 Redirect 하기 위해 Handler를 정의한다
 * 정의한 Handler는 WebSecurity에서 사용된다
 **/
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    // 생성자
    public LoginSuccessHandler(String defaultUrl) {
        setDefaultTargetUrl(defaultUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session != null) {
            String redirectUrl = (String) session.getAttribute("previousPage");
            String endPoint = redirectUrl.substring(redirectUrl.length()-12, redirectUrl.length());
            if(!endPoint.equals("/user/signup")) {
                // Redirect 주소가 존재하는 경우 세션 정보를 지우고 이전 페이지로 Redirect 한다
                session.removeAttribute("previousPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
