package com.minguccicommerce.payment_service.dto;

import lombok.Data;

@Data
public class TossPaymentConfirmRequest {
    private String paymentKey;
    private String orderId;
    private int amount;
}
