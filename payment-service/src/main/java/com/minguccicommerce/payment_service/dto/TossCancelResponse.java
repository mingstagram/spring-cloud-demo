package com.minguccicommerce.payment_service.dto;

import lombok.Data;

@Data
public class TossCancelResponse {
    private String paymentKey;
    private String orderId;
    private String status;
    private int cancelAmount;
    private String canceledAt;
    private String cancelReason;
}