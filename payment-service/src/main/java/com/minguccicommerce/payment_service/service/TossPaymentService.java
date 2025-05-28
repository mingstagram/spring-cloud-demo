package com.minguccicommerce.payment_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final WebClient tossWebClient;


}
