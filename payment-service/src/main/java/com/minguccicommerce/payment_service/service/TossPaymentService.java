package com.minguccicommerce.payment_service.service;

import com.minguccicommerce.payment_service.client.OrderClient;
import com.minguccicommerce.payment_service.dto.*;
import com.minguccicommerce.payment_service.entity.Payment;
import com.minguccicommerce.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TossPaymentService {

    private final WebClient tossWebClient;
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public TossPaymentConfirmResponse confirmPayment(TossPaymentConfirmRequest request) {
        // Toss로 결제 승인 요청
        TossPaymentConfirmResponse response = tossWebClient.post()
                .uri("/payments/confirm")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse ->
                        clientResponse.bodyToMono(String.class).map(body -> {
                            log.error("💥 Toss API Error Response: {}", body);
                            return new RuntimeException("Toss API 호출 실패: " + body);
                        })
                )
                .bodyToMono(TossPaymentConfirmResponse.class)
                .block();

        log.info("✅ Toss 결제 승인 응답 수신: {}", response);

        // 응답을 Payment 엔티티로 매핑
        Payment payment = Payment.builder()
                .orderId(response.getOrderId())
                .amount(response.getTotalAmount())
                .status(PaymentStatus.COMPLETED)
                .pgTransactionId(response.getPaymentKey())
                .requestedAt(response.getRequestedAt().toLocalDateTime())
                .completedAt(response.getApprovedAt().toLocalDateTime())
                .build();

        // DB 저장
        paymentRepository.save(payment);
        log.info("💾 결제 정보 저장 완료: {}", payment.getId());

        // [TODO] 주문 상태 업데이트 (프론트 개발 이후 처리 예정)
        /*
        orderClient.updateOrderStatus(response.getOrderId(), OrderStatus.PAID);
        */

        return response;
    }

    public TossCancelResponse cancelPayment(TossCancelRequest request) {
        TossCancelResponse response = tossWebClient.post()
                .uri("/payments/{paymentKey}/cancel", request.getPaymentKey())
                .bodyValue(Map.of("cancelReason", request.getCancelReason()))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                                    log.error("❌ Toss 결제 취소 API 오류: {}", errorBody);
                                    return Mono.error(new RuntimeException("Toss 결제 취소 실패: " + errorBody));
                                }))
                .bodyToMono(TossCancelResponse.class)
                .doOnNext(res -> log.info("🛑 결제 취소 완료: {}", res))
                .doOnError(error -> log.error("❌ 결제 취소 중 예외 발생", error))
                .block();

        // DB에 상태 업데이트 (선택)
        paymentRepository.findByPgTransactionId(request.getPaymentKey())
                .ifPresent(payment -> {
                    payment.fail(); // 실패 상태로 변경
                    paymentRepository.save(payment);
                });

        return response;
    }

}
