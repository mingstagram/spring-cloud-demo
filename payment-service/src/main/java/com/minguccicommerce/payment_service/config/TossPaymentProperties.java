package com.minguccicommerce.payment_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "toss")
public class TossPaymentProperties {
    private String clientKey;
    private String secretKey;
}
