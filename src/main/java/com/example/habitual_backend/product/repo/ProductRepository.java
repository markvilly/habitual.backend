package com.example.habitual_backend.product.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findById(Long id);
    List<Product> findByCategory(Long categoryId);
    List<Product> findByTrendingTrue();
    List<Product> findByPopularTrue();
    
}
