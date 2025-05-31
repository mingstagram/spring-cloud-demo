package com.minguccicommerce.payment_service.controller;

import com.minguccicommerce.common_library.dto.ApiResponse;
import com.minguccicommerce.payment_service.dto.TossCancelRequest;
import com.minguccicommerce.payment_service.dto.TossCancelResponse;
import com.minguccicommerce.payment_service.dto.TossPaymentConfirmRequest;
import com.minguccicommerce.payment_service.dto.TossPaymentConfirmResponse;
import com.minguccicommerce.payment_service.service.TossPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final TossPaymentService tossPaymentService;

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<TossPaymentConfirmResponse>> success(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam int amount
    ) {
        TossPaymentConfirmRequest request = new TossPaymentConfirmRequest();
        request.setPaymentKey(paymentKey);
        request.setOrderId(orderId);
        request.setAmount(amount);

        TossPaymentConfirmResponse response = tossPaymentService.confirmPayment(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/fail")
    public String fail() {
        return "결제요청 실패";
    }

    @PostMapping("/toss/cancel")
    public ResponseEntity<ApiResponse<TossCancelResponse>> cancel(
            @RequestBody TossCancelRequest request
    ) {
        TossCancelResponse response = tossPaymentService.cancelPayment(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

}
