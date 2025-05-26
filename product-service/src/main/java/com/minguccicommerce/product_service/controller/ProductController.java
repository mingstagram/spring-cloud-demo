package com.minguccicommerce.product_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> list = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

}
