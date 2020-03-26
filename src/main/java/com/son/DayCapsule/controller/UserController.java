package com.son.DayCapsule.controller;

import com.son.DayCapsule.domain.User;
import com.son.DayCapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String root() {
        return "redirect:/user/signin";
    }

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
    public String signin(Model model) {
        model.addAttribute("signinForm", new SigninForm());
        return "signin";
    }

    @GetMapping("/user/main")
    public String signin() {
        return "home";
    }

}
