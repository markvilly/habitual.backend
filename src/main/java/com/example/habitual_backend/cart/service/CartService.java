package com.example.habitual_backend.cart.service;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;
import com.example.habitual_backend.cart.dto.CartAddRequest;
import com.example.habitual_backend.cart.dto.CartItemDto;
import com.example.habitual_backend.cart.dto.CartResponse;
import com.example.habitual_backend.cart.model.CartItem;
import com.example.habitual_backend.cart.repository.CartItemRepository;
import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartResponse getCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<CartItem> items = cartItemRepository.findByUser(user);
        return buildCartResponse(items);
    }

    public CartResponse addItemToCart(Long userId, CartAddRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItemOpt = cartItemRepository.findByUserAndProduct(user, product);
        
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            if (request.getQuantity() <= 0) {
                newItem.setQuantity(1); // default to 1 if bad request
            } else {
                newItem.setQuantity(request.getQuantity());
            }
            cartItemRepository.save(newItem);
        }

        return getCartForUser(userId);
    }

    public CartResponse removeItemFromCart(Long userId, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
                
        if (!item.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        cartItemRepository.delete(item);
        
        return getCartForUser(userId);
    }
    
    public List<Product> getSimilarItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        
        if (cartItems.isEmpty()) {
            // Return trending products if cart is empty
            return productRepository.findByTrendingTrue().stream()
                    .limit(5)
                    .collect(Collectors.toList());
        }
        
        // Find categories of items in cart
        Set<Long> categoryIds = cartItems.stream()
                .map(item -> item.getProduct().getCategory().getId())
                .collect(Collectors.toSet());
                
        Set<Long> productIdsInCart = cartItems.stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toSet());
                
        List<Product> similarProducts = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            List<Product> categoryProducts = productRepository.findByCategoryId(categoryId);
            for (Product p : categoryProducts) {
                if (!productIdsInCart.contains(p.getId())) {
                    similarProducts.add(p);
                }
            }
        }
        
        // Return up to 5 similar products, or padding with trending if needed
        if (similarProducts.isEmpty()) {
            return productRepository.findByTrendingTrue().stream()
                    .filter(p -> !productIdsInCart.contains(p.getId()))
                    .limit(5)
                    .collect(Collectors.toList());
        }
        
        return similarProducts.stream().limit(5).collect(Collectors.toList());
    }

    private CartResponse buildCartResponse(List<CartItem> items) {
        List<CartItemDto> dtos = new ArrayList<>();
        double total = 0;
        
        for (CartItem item : items) {
            Product p = item.getProduct();
            double subTotal = p.getPrice() * item.getQuantity();
            total += subTotal;
            
            CartItemDto dto = new CartItemDto(
                    item.getId(),
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getImageURL(),
                    item.getQuantity(),
                    subTotal
            );
            dtos.add(dto);
        }
        
        return new CartResponse(dtos, total);
    }
}
