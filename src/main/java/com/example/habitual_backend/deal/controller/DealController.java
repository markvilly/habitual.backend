package com.example.habitual_backend.deal.controller;

import com.example.habitual_backend.deal.model.Deal;
import com.example.habitual_backend.deal.service.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
public class DealController {
    
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Deal>> getTrendingDeals() {
        return ResponseEntity.ok(dealService.getTrendingDeals());
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Deal>> getDealsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(dealService.getDealsByCategory(categoryId));
    }

    @GetMapping("/for-you")
    public ResponseEntity<List<Deal>> getPersonalizedDeals(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(dealService.getPersonalizedDeals(userId));
    }
}
