package com.minguccicommerce.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private String productName;
    private int quantity;
}