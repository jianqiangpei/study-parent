package com.study.api.client.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.studyframework.core.model.ApiResponse;

@FeignClient(value = "study-order")
public interface OrderApiClient {

    @GetMapping(value = "/rest/order")
    ApiResponse<String> getOrderId();


}
