package com.minguccicommerce.payment_service.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TossPaymentConfirmResponse {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String status;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;
    private String type; // NORMAL 등
    private String method; // 카드, 가상계좌 등
    private String country;
    private boolean isPartialCancelable;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;

    private Card card;
    private EasyPay easyPay;
    private Receipt receipt;
    private Checkout checkout;

    @Data
    public static class Card {
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private int amount;
    }

    @Data
    public static class EasyPay {
        private String provider;
        private int amount;
        private int discountAmount;
    }

    @Data
    public static class Receipt {
        private String url;
    }

    @Data
    public static class Checkout {
        private String url;
    }
}