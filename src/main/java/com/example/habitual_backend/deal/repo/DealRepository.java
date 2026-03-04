package com.example.habitual_backend.deal.repo;

import com.example.habitual_backend.deal.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    
    @Query("SELECT d FROM Deal d WHERE d.active = true AND d.product.trending = true")
    List<Deal> findTrendingDeals();

    @Query("SELECT d FROM Deal d WHERE d.active = true AND d.product.category.id = :categoryId")
    List<Deal> findDealsByCategory(@Param("categoryId") Long categoryId);

    List<Deal> findByActiveTrue();
}
