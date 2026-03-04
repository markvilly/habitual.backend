package com.example.habitual_backend.wishlist.service;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;
import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.ProductRepository;
import com.example.habitual_backend.wishlist.dto.WishlistAddRequest;
import com.example.habitual_backend.wishlist.dto.WishlistItemDto;
import com.example.habitual_backend.wishlist.dto.WishlistResponse;
import com.example.habitual_backend.wishlist.dto.WishlistShareResponse;
import com.example.habitual_backend.wishlist.model.Wishlist;
import com.example.habitual_backend.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public WishlistResponse getWishlistForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        List<Wishlist> items = wishlistRepository.findByUser(user);
        return buildWishlistResponse(items);
    }

    public WishlistResponse addItemToWishlist(Long userId, WishlistAddRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Wishlist> existingItemOpt = wishlistRepository.findByUserAndProduct(user, product);
        
        if (existingItemOpt.isEmpty()) {
            Wishlist newItem = new Wishlist();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setAddedAt(LocalDateTime.now());
            wishlistRepository.save(newItem);
        }

        return getWishlistForUser(userId);
    }

    public WishlistResponse removeItemFromWishlist(Long userId, Long wishlistItemId) {
        Wishlist item = wishlistRepository.findById(wishlistItemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
                
        if (!item.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        wishlistRepository.delete(item);
        
        return getWishlistForUser(userId);
    }
    
    public List<Product> getSimilarItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        List<Wishlist> wishlistItems = wishlistRepository.findByUser(user);
        
        if (wishlistItems.isEmpty()) {
            // Return trending products if wishlist is empty
            return productRepository.findByTrendingTrue().stream()
                    .limit(5)
                    .collect(Collectors.toList());
        }
        
        // Find categories of items in wishlist
        Set<Long> categoryIds = wishlistItems.stream()
                .map(item -> item.getProduct().getCategory().getId())
                .collect(Collectors.toSet());
                
        Set<Long> productIdsInWishlist = wishlistItems.stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toSet());
                
        List<Product> similarProducts = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            List<Product> categoryProducts = productRepository.findByCategoryId(categoryId);
            for (Product p : categoryProducts) {
                if (!productIdsInWishlist.contains(p.getId())) {
                    similarProducts.add(p);
                }
            }
        }
        
        // Return up to 5 similar products, or padding with trending if needed
        if (similarProducts.isEmpty()) {
            return productRepository.findByTrendingTrue().stream()
                    .filter(p -> !productIdsInWishlist.contains(p.getId()))
                    .limit(5)
                    .collect(Collectors.toList());
        }
        
        return similarProducts.stream().limit(5).collect(Collectors.toList());
    }

    public WishlistShareResponse shareWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // We generate a simple shareable URL token for demonstration purposes.
        // In a real application, you might save this token to the database and link it to the user's wishlist.
        String shareToken = UUID.randomUUID().toString();
        String shareUrl = "https://habitual.com/wishlist/shared/" + shareToken;
        
        return new WishlistShareResponse(shareUrl);
    }

    private WishlistResponse buildWishlistResponse(List<Wishlist> items) {
        List<WishlistItemDto> dtos = new ArrayList<>();
        
        for (Wishlist item : items) {
            Product p = item.getProduct();
            
            WishlistItemDto dto = new WishlistItemDto(
                    item.getId(),
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getImageURL(),
                    item.getAddedAt()
            );
            dtos.add(dto);
        }
        
        return new WishlistResponse(dtos);
    }
}
