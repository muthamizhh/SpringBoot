package com.example.myWebApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        return "In the login page";
    }
}
