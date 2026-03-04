package com.example.habitual_backend.deal.service;

import com.example.habitual_backend.deal.model.Deal;
import com.example.habitual_backend.deal.repo.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {
    private final DealRepository dealRepo;

    public DealService(DealRepository dealRepo) {
        this.dealRepo = dealRepo;
    }

    public List<Deal> getTrendingDeals() {
        return dealRepo.findTrendingDeals();
    }

    public List<Deal> getDealsByCategory(Long categoryId) {
        return dealRepo.findDealsByCategory(categoryId);
    }

    public List<Deal> getPersonalizedDeals(Long userId) {
        // TODO: Map to User interests when user preferences are fully implemented
        return dealRepo.findByActiveTrue();
    }
}
