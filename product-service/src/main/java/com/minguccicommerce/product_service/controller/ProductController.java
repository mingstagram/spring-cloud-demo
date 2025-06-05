package com.minguccicommerce.product_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.product_service.dto.ProductRequest;
import com.minguccicommerce.product_service.dto.ProductResponse;
import com.minguccicommerce.product_service.dto.ProductSearchRequest;
import com.minguccicommerce.product_service.dto.ProductSearchResponse;
import com.minguccicommerce.product_service.entity.Product;
import com.minguccicommerce.product_service.service.ProductSearchService;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

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

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>>  registerProduct(@RequestBody ProductRequest request) {
        Product saved = productService.registerProduct(request);

        ProductResponse response = new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getCategory()
        );

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<ProductSearchResponse>> searchProducts(@RequestBody ProductSearchRequest request) {
        ProductSearchResponse results = productSearchService.search(request);
        return ResponseEntity.ok(ApiResponse.ok(results));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        ProductResponse updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.ok(updated));
    }

}
