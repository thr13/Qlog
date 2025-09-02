package com.qlog.backend.user.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/users")
public class UserViewController {

    @GetMapping("/signup")
    public String signupPage() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }
}
