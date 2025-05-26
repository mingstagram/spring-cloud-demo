package com.minguccicommerce.user_service.client;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.user_service.dto.EmailVerifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service")
public interface EmailVerifyClient {

    @PostMapping("/verify")
    ApiResponse<String> verifyCode(@RequestBody EmailVerifyRequest request);
}
