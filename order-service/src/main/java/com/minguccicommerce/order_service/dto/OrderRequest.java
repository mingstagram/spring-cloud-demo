package com.minguccicommerce.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 수량은 1개입니다.")
    private Integer quantity;
}