package com.minguccicommerce.common_library;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDetail {
    private String field;
    private String message;
}