package com.example.habitual_backend.cart.controller;

import com.example.habitual_backend.cart.dto.CartAddRequest;
import com.example.habitual_backend.cart.dto.CartResponse;
import com.example.habitual_backend.cart.service.CartService;
import com.example.habitual_backend.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(
            @RequestParam Long userId,
            @RequestBody CartAddRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, request));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<CartResponse> removeItem(
            @RequestParam Long userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, itemId));
    }

    @GetMapping("/similar")
    public ResponseEntity<List<Product>> getSimilarItems(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getSimilarItems(userId));
    }
}
