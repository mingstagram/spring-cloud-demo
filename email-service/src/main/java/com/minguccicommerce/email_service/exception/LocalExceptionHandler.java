package com.minguccicommerce.email_service.exception;

import com.minguccicommerce.common_library.dto.ApiResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LocalExceptionHandler {

    @ExceptionHandler({EmailCodeNotFoundException.class, EmailCodeMismatchException.class})
    public ResponseEntity<ApiResponse<?>> handleEmailErrors(RuntimeException e) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ email-service LocalExceptionHandler 등록됨");
    }
}