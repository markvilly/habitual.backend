package com.example.habitual_backend.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private double productPrice;
    private String productImageURL;
    private int quantity;
    private double subTotal;
}
