package com.study.goods.controller;

import com.study.api.client.order.OrderApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyframework.core.model.ApiResponse;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Resource
    private OrderApiClient orderApiClient;

    @GetMapping
    public ApiResponse<String> getUserOrder(){

        ApiResponse<String> response = orderApiClient.getOrderId();
        System.out.println(response.getData());

        return ApiResponse.ok("1");
    }

}
