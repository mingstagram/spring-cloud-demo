package com.minguccicommerce.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductSearchResponse {
    private List<ProductResponse> products;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
