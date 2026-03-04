package com.example.habitual_backend.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }
    
    public List<Product> getProductsByCategory(Long categoryId){
        return productRepo.findByCategory(categoryId);
    }
    
}
