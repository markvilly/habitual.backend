package com.example.habitual_backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// Assuming all items in the cart are purchased
public class OrderPlaceRequest {
    // Left empty on purpose. Could take payment or shipping details here in the future.
}
