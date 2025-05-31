package com.minguccicommerce.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
}