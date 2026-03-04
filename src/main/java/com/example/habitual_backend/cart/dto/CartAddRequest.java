package com.example.habitual_backend.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {
    private Long productId;
    private int quantity;
}
