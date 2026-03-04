package com.example.habitual_backend.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.product.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    List<Product> findByTrendingTrue();
    List<Product> findByPopularTrue();

    @Query("SELECT p FROM Product p WHERE " +
           "(:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("q") String q,
                                 @Param("categoryId") Long categoryId,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
}
