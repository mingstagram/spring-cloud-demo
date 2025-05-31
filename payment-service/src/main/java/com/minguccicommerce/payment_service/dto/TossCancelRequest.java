package com.minguccicommerce.payment_service.dto;

import lombok.Data;

@Data
public class TossCancelRequest {
    private String paymentKey;
    private String cancelReason;
}