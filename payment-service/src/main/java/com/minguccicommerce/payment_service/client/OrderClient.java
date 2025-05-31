package com.minguccicommerce.payment_service.client;

import com.minguccicommerce.payment_service.dto.OrderStatusUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderClient {

    @PatchMapping("/{orderId}/status")
    void updateOrderStatus(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderStatusUpdateRequest request
            );

}
