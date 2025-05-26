package com.minguccicommerce.order_service.dto;

import com.minguccicommerce.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}