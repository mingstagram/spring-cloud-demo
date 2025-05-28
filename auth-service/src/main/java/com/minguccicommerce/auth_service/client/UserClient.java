package com.minguccicommerce.auth_service.client;

import com.minguccicommerce.auth_service.config.FeignClientConfig;
import com.minguccicommerce.auth_service.config.FeignConfig;
import com.minguccicommerce.auth_service.dto.UserResponse;
import com.minguccicommerce.common_library.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "user-service",
        configuration = FeignConfig.class,
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {

    @GetMapping("/by-email")
    ApiResponse<UserResponse> getUserByEmail(@RequestParam("email") String email);
}
