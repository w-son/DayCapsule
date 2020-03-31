package com.son.DayCapsule.config;

import com.son.DayCapsule.controller.SigninForm;
import com.son.DayCapsule.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

/** 서블릿과 컨트롤러 사이의 필터에서 HttpServletRequest와 HttpServletResponse를 가로챌 인터셉터, WebConfigurer를 상속받은 클래스에서 등록한다 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 사실 로그인 관련해서 이 과정을 Spring Security가 대신 해준다 HttpSecurity에서 설정 가능
    private static final String LOGIN = "login";

    /** 매핑 되기 전 처리 **/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // if(request.getMethod().equals("GET")) 이런 식의 조건 사용 가능
        HttpSession session = request.getSession();

        // 만약 이전 로그인 세션 기록이 남아있다면 지운다
        if(session.getAttribute(LOGIN) != null) {
            UserDetails user = (UserDetails) session.getAttribute(LOGIN);
            System.out.println(user.getUsername() + " 의 세션 정보를 삭제하였습니다.");
            session.removeAttribute(LOGIN);
        }
        return true;
    }

    /** 매핑 되고 난 후의 처리 **/
    /* 이 부분을 처리하게 되면 기존의 Controller에서 void 리턴 이후의 처리를 이 메서드에서 실행하게 된다 */
    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView) {
        HttpSession session = request.getSession();
        ModelMap modelMap = modelAndView.getModelMap();
        UserDetails user = (UserDetails) modelMap.get("user");

        session.setAttribute(LOGIN, user);
    }

    /*
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

    }
    */
}
