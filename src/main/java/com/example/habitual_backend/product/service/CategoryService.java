package com.example.habitual_backend.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.habitual_backend.product.model.Category;
import com.example.habitual_backend.product.repo.CategoryRepository;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo){
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
    }

}
