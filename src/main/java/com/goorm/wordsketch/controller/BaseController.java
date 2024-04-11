package com.goorm.wordsketch.controller;

import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BaseController {

    @GetMapping("/")
    public String index(Token token) {
        System.out.println("token = " + token);
        return "index.html";
    }

    @GetMapping("/user")
    public String user() {
        return "user.html";
    }

    @RequestMapping("/accessDenited")
    public String accessDenied() {
        return "accessDenied.html";
    }
}
