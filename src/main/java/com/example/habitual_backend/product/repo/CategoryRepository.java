package com.example.habitual_backend.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.product.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
