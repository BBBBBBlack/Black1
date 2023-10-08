package com.example.tea_demo.controller;

import com.example.tea_demo.domain.ResponseResult;
import com.example.tea_demo.domain.User;
import com.example.tea_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResponseResult login(@RequestParam String userEmail,
                                @RequestParam String password) {
        User user = new User();
        user.setUserEmail(userEmail);
        user.setPassword(password);
        return userService.login(user);
    }

    @RequestMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }


    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('all:login')")
    public String hello() {
        return "hello";
    }
}
