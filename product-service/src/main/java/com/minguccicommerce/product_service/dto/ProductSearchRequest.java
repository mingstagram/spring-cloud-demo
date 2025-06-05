package com.minguccicommerce.product_service.dto;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String keyword;
    private int page = 0;
    private int size = 10;

    private String sortField = "id";
    private String sortDirection = "asc";
}
