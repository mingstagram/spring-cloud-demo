package com.minguccicommerce.user_service.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("현재 비밀번호가 일치하지 않습니다.");
    }
}