package com.study.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class controller {

    @Value("${study.test}")
    private String test;

    @Value("${study.pjq}")
    private String pjq;


    @GetMapping
    public String test(){

        return test + "-----" + pjq;
    }

}
