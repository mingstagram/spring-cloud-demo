package com.minguccicommerce.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto implements Serializable {
    private Long productId;
    private int quantity;
}
