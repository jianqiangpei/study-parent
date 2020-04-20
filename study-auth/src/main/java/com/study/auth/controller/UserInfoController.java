package com.study.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping
@RestController
public class UserInfoController {

    @GetMapping("/user/info")
    public Principal userInfo(Principal user){

        return user;
    }


}
