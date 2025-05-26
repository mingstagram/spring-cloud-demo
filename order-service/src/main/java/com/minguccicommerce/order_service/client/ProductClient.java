package com.minguccicommerce.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/internal/{id}/decrease-stock")
    void decreaseStock(@PathVariable("id") Long productId, @RequestParam("quantity") int quantity);

}
