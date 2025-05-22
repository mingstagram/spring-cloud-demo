package com.minguccicommerce.common_library.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final List<FieldErrorDetail> errors;

    public ValidationErrorResponse(BindingResult bindingResult) {
        this.errors = bindingResult.getFieldErrors().stream()
                .map(e -> new FieldErrorDetail(e.getField(), e.getDefaultMessage()))
                .toList();
    }
}