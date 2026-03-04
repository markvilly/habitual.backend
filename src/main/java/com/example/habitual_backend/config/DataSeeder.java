package com.example.habitual_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.habitual_backend.product.model.Category;
import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.product.repo.CategoryRepository;
import com.example.habitual_backend.product.repo.ProductRepository;
import com.example.habitual_backend.deal.model.Deal;
import com.example.habitual_backend.deal.repo.DealRepository;

@Component
public class DataSeeder implements CommandLineRunner {
    
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final DealRepository dealRepo;

    public DataSeeder(CategoryRepository categoryRepo, ProductRepository productRepo, DealRepository dealRepo){
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.dealRepo = dealRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if(categoryRepo.count() == 0 && productRepo.count() == 0){
            Category cat1 = new Category(null, "Electronics", "Electronics", "https://example.com/electronics.jpg");
            Category cat2 = new Category(null, "Books", "Books", "https://example.com/books.jpg");
            categoryRepo.save(cat1);
            categoryRepo.save(cat2);

            Product prod1 = new Product(null, "Smartphone", "High-end smartphone", "https://example.com/smartphone.jpg", 800, true, true, cat1);
            Product prod2 = new Product(null, "Sci-Fi Novel", "Bestselling sci-fi novel", "https://example.com/scifi.jpg", 20, true, true, cat2);
            Product prod3 = new Product(null, "Laptop", "Powerful laptop", "https://example.com/laptop.jpg", 1500, true, true, cat1);
            Product prod4 = new Product(null, "Data Science Book", "Learn data science", "https://example.com/datascience.jpg", 45, true, true, cat2);
            Product prod5 = new Product(null, "Headphones", "Noise cancelling", "https://example.com/headphones.jpg", 150, true, true, cat1);
            Product prod6 = new Product(null, "History Book", "World history", "https://example.com/history.jpg", 30, true, true, cat2);
            
            productRepo.save(prod1);
            productRepo.save(prod2);
            productRepo.save(prod3);
            productRepo.save(prod4);
            productRepo.save(prod5);
            productRepo.save(prod6);

            if (dealRepo.count() == 0) {
                Deal deal1 = new Deal(null, prod1, 750.0, true);
                Deal deal2 = new Deal(null, prod4, 30.0, true);
                Deal deal3 = new Deal(null, prod5, 120.0, true);
                dealRepo.save(deal1);
                dealRepo.save(deal2);
                dealRepo.save(deal3);
            }
        }
    }
}
