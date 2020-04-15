package com.son.DayCapsule.controller;

import com.son.DayCapsule.controller.fom.SigninForm;
import com.son.DayCapsule.controller.fom.SignupForm;
import com.son.DayCapsule.domain.User;
import com.son.DayCapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(SignupForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userService.signup(user);
        return "redirect:/user/signin";
    }

    @GetMapping("/user/signin")
    public String signin(HttpServletRequest request, Model model) {
        // 이전 페이지의 정보를 세션에 저장한다
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("previousPage", referrer);

        model.addAttribute("signinForm", new SigninForm());
        return "signin";
    }

}
