package com.example.order_service.client;

import com.example.order_service.config.FeignClientConfig;
import com.example.order_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        fallbackFactory = UserClientFallbackFactory.class,
        configuration = FeignClientConfig.class
)
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
