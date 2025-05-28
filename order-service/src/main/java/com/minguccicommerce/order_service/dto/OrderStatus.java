package com.minguccicommerce.order_service.dto;

public enum OrderStatus {
    CREATED, // 생성됨
    PAID,    // 결제 완료
    SHIPPED, // 배송 중
    CANCELLED // 취소됨
}