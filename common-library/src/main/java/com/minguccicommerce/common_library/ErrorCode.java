package com.minguccicommerce.common_library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    PRODUCT_OUT_OF_STOCK("상품 재고가 부족합니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_ERROR("서버 내부 오류입니다.");

    private final String message;
}