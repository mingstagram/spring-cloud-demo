package com.minguccicommerce.product_service.exception;

import com.minguccicommerce.common_library.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LocalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<?>> handleStockException(InsufficientStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(e.getMessage()));
    }
}
