package com.example.habitual_backend.order.controller;

import com.example.habitual_backend.order.dto.OrderResponse;
import com.example.habitual_backend.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    }

    @GetMapping("/in-progress")
    public ResponseEntity<List<OrderResponse>> getInProgressOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getInProgressOrders(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @RequestParam Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(userId, id));
    }
}
