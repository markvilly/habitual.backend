package com.example.habitual_backend.interests.controller;

import com.example.habitual_backend.interests.service.InterestsService;
import com.example.habitual_backend.product.model.Category;
import com.example.habitual_backend.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
public class InterestsController {

    private final InterestsService interestsService;

    public InterestsController(InterestsService interestsService) {
        this.interestsService = interestsService;
    }

    @GetMapping("/discover")
    public ResponseEntity<List<Product>> discoverProducts(@RequestParam Long userId) {
        return ResponseEntity.ok(interestsService.discoverProducts(userId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getInterestCategories(@RequestParam Long userId) {
        return ResponseEntity.ok(interestsService.getInterestCategories(userId));
    }
}
