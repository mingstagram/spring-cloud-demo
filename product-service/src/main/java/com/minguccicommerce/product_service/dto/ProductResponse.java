package com.minguccicommerce.product_service.dto;

import com.minguccicommerce.product_service.entity.Product;
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

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
