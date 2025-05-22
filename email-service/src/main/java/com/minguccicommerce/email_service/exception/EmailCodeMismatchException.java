package com.minguccicommerce.email_service.exception;

public class EmailCodeMismatchException extends RuntimeException {
    public EmailCodeMismatchException(String message) {
        super(message);
    }
}