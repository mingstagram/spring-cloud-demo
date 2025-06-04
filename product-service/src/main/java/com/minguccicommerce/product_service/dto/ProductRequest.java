package com.minguccicommerce.product_service.dto;

import lombok.Getter;

@Getter
public class ProductRequest {
    private String name;
    private String description;
    private int price;
    private int stock;
    private String category;
}