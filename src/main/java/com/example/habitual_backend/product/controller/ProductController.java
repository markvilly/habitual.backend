package com.example.habitual_backend.product.controller;


import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }
    @GetMapping("/trending")
    public ResponseEntity<List<Product>> getTrendingProducts(){
        return ResponseEntity.ok(productService.getTrendingProducts());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Product>> getPopularProducts(){
        return ResponseEntity.ok(productService.getPopularProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.searchProducts(q, categoryId, minPrice, maxPrice));
    }
}
