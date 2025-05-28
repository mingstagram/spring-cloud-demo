package com.minguccicommerce.product_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class ProductInternalController {

    private final ProductService productService;

    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<ApiResponse<Void>> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        productService.decreaseStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<ApiResponse<Void>> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        productService.increaseStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
