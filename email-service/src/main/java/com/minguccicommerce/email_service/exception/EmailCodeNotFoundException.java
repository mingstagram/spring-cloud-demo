package com.minguccicommerce.email_service.exception;

public class EmailCodeNotFoundException extends RuntimeException {
    public EmailCodeNotFoundException(String message) {
        super(message);
    }
}