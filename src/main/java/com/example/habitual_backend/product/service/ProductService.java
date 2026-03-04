package com.example.habitual_backend.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.ProductRepository;

import io.micrometer.common.lang.NonNull;

@Service
public class ProductService {
    
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(@NonNull Long id){
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }
    
    public List<Product> getProductsByCategory(Long categoryId){
        return productRepo.findByCategoryId(categoryId);
    }
    public List<Product> getTrendingProducts(){
        return productRepo.findByTrendingTrue();
    }

    public List<Product> getPopularProducts(){
        return productRepo.findByPopularTrue();
    }

    public List<Product> searchProducts(String q, Long categoryId, Double minPrice, Double maxPrice){
        return productRepo.searchProducts(q, categoryId, minPrice, maxPrice);
    }
}
