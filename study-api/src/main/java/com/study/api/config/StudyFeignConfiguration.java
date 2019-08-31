package com.study.api.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
@EnableFeignClients(basePackages = {"com.study.api.client"})
public class StudyFeignConfiguration {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        if ("X-AUTH".equalsIgnoreCase(name)) {
                            String values = request.getHeader(name);
                            requestTemplate.header("X-AUTH", values);
                        }
                        if ("CURRENT_COMPANY_ID".equalsIgnoreCase(name)) {
                            String values = request.getHeader(name);
                            requestTemplate.header("current_company_id", values);
                        }
                    }
                }
            }
        };
    }

}
