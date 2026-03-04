package com.example.habitual_backend.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private double productPrice;
    private String productImageURL;
    private LocalDateTime addedAt;
}
