package com.minguccicommerce.order_service.dto;

import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private OrderStatus status;
}
