package com.study.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching(proxyTargetClass = true)
@EntityScan(basePackages = {"com.study.auth.domain.entity"})
public class StudyAuthApplication {


    public static void main(String[] args) {

        SpringApplication.run(StudyAuthApplication.class , args);
    }
}
