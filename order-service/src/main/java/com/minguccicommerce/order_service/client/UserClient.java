package com.minguccicommerce.order_service.client;

import com.minguccicommerce.order_service.config.FeignClientConfig;
import com.minguccicommerce.order_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8000",
        configuration = FeignClientConfig.class,
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {
    @GetMapping("/user/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
