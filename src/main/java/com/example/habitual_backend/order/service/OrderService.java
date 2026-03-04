package com.example.habitual_backend.order.service;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;
import com.example.habitual_backend.cart.model.CartItem;
import com.example.habitual_backend.cart.repository.CartItemRepository;
import com.example.habitual_backend.order.dto.OrderItemDto;
import com.example.habitual_backend.order.dto.OrderResponse;
import com.example.habitual_backend.order.model.OrderEntity;
import com.example.habitual_backend.order.model.OrderItem;
import com.example.habitual_backend.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PROCESSING"); // initial status

        double totalAmount = 0.0;
        
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            
            order.addItem(orderItem);
            
            totalAmount += (orderItem.getPriceAtPurchase() * orderItem.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        
        OrderEntity savedOrder = orderRepository.save(order);
        
        // Clear the user's cart
        cartItemRepository.deleteAll(cartItems);

        return mapToDto(savedOrder);
    }

    public List<OrderResponse> getOrderHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserOrderByOrderDateDesc(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getInProgressOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserAndStatusIn(user, Arrays.asList("PENDING", "PROCESSING"))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long userId, Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToDto(order);
    }

    private OrderResponse mapToDto(OrderEntity order) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                itemDtos.add(new OrderItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()
                ));
            }
        }
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                itemDtos
        );
    }
}
