package com.example.habitual_backend.wishlist.controller;

import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.wishlist.dto.WishlistAddRequest;
import com.example.habitual_backend.wishlist.dto.WishlistResponse;
import com.example.habitual_backend.wishlist.dto.WishlistShareResponse;
import com.example.habitual_backend.wishlist.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<WishlistResponse> getWishlist(@RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlistForUser(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistResponse> addItem(
            @RequestParam Long userId,
            @RequestBody WishlistAddRequest request) {
        return ResponseEntity.ok(wishlistService.addItemToWishlist(userId, request));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<WishlistResponse> removeItem(
            @RequestParam Long userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(wishlistService.removeItemFromWishlist(userId, itemId));
    }

    @GetMapping("/similar")
    public ResponseEntity<List<Product>> getSimilarItems(@RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.getSimilarItems(userId));
    }
    
    @PostMapping("/share")
    public ResponseEntity<WishlistShareResponse> shareWishlist(@RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.shareWishlist(userId));
    }
}
