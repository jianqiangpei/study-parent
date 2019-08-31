package com.study.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudyConfigServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(StudyConfigServerApplication.class , args);
    }

}
