package com.minguccicommerce.cart_service.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minguccicommerce.common_library.dto.ApiResponse;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LocalExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleFeignNotFound(FeignException.NotFound ex) {
        String message = extractErrorMessage(ex);
        return ResponseEntity.ok(ApiResponse.fail(message)); // 그대로 사용자에게 전달
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ cart-service LocalExceptionHandler 등록됨");
    }

    // Feign 오류 메시지 파싱
    private String extractErrorMessage(FeignException ex) {
        try {
            String body = ex.contentUTF8();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);
            JsonNode messageNode = root.get("message");
            return (messageNode != null && !messageNode.isNull()) ? messageNode.asText() : "연동 서비스 오류가 발생했습니다.";
        } catch (Exception e) {
            return "연동 서비스 오류가 발생했습니다.";
        }
    }
}
