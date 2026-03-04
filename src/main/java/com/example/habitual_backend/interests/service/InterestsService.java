package com.example.habitual_backend.interests.service;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;
import com.example.habitual_backend.product.model.Category;
import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.CategoryRepository;
import com.example.habitual_backend.product.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterestsService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public InterestsService(UserRepository userRepository,
                            ProductRepository productRepository,
                            CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Returns products from categories that match the user's interests.
     * Falls back to trending products if no interests are set.
     */
    public List<Product> discoverProducts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> interests = parseInterests(user.getInterests());
        if (interests.isEmpty()) {
            // fallback: return trending products
            return productRepository.findByTrendingTrue().stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }

        List<Category> matchedCategories = getMatchingCategories(interests);
        if (matchedCategories.isEmpty()) {
            return productRepository.findByTrendingTrue().stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }

        List<Product> products = new ArrayList<>();
        for (Category category : matchedCategories) {
            products.addAll(productRepository.findByCategoryId(category.getId()));
        }

        return products.stream().limit(10).collect(Collectors.toList());
    }

    /**
     * Returns categories that match the user's interests.
     */
    public List<Category> getInterestCategories(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> interests = parseInterests(user.getInterests());
        if (interests.isEmpty()) {
            // fallback: return all categories
            return categoryRepository.findAll();
        }

        return getMatchingCategories(interests);
    }

    /**
     * Converts a CSV interest string from the user to a list of lowercase keywords.
     */
    private List<String> parseInterests(String interestsStr) {
        if (interestsStr == null || interestsStr.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(interestsStr.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Matches category names (case-insensitively) against the list of interest keywords.
     */
    private List<Category> getMatchingCategories(List<String> interests) {
        return categoryRepository.findAll().stream()
                .filter(cat -> interests.stream()
                        .anyMatch(interest -> cat.getName().toLowerCase().contains(interest)
                                || interest.contains(cat.getName().toLowerCase())))
                .collect(Collectors.toList());
    }
}
