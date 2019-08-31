package com.study.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StudyOrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(StudyOrderApplication.class , args);
    }

}
