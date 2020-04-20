package com.study.goods.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyframework.core.model.ApiResponse;

@RestController
@RequestMapping
public class GoodsController {


    @GetMapping("goods")
    public ApiResponse goods(){

        return ApiResponse.ok();
    }


    @GetMapping("/goods/ig")
    public ApiResponse goodsIg(){

        return ApiResponse.ok(null , "喔喔喔");
    }
}
