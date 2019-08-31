package com.study.order.controller;

import com.study.order.domain.ro.OrderRO;
import org.springframework.web.bind.annotation.*;
import org.studyframework.core.model.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/rest/order")
public class OrderController {


    @GetMapping
    public ApiResponse<String> getOrderId(HttpServletRequest request , HttpServletResponse response){

        return ApiResponse.ok(UUID.randomUUID().toString());
    }

    @GetMapping("/nice")
    public ApiResponse nice(){

        System.out.println("________--------------__________---------________--");

        return ApiResponse.ok();
    }

    @PostMapping
    public ApiResponse save(@RequestBody OrderRO ro){

        return ApiResponse.ok();
    }



}
