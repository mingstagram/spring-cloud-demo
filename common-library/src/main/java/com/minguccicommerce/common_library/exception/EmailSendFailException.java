package com.minguccicommerce.common_library.exception;

public class EmailSendFailException extends RuntimeException {
    public EmailSendFailException(String message) {
        super(message);
    }
}