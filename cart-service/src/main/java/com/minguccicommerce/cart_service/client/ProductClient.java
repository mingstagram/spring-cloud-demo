package com.minguccicommerce.cart_service.client;

import com.minguccicommerce.cart_service.dto.ProductResponse;
import com.minguccicommerce.common_library.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long id);
}
