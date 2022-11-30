package com.yeshtery.picturepublishing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    @GetMapping("/login")
    public Principal post(Principal principal) {
        return principal;
    }
}
