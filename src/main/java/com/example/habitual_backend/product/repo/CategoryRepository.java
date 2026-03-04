package com.example.habitual_backend.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.product.model.Category;
import com.example.habitual_backend.product.model.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Product> findByCategory(Category categoryId);
    List<Product> findByTrendingTrue();
    List<Product> findByPopularTrue();
    

}
