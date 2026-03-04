package com.example.habitual_backend.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByTrendingTrue();
    List<Product> findByPopularTrue();
}
