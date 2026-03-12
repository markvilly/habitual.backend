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
            Category cat1 = new Category(null, "Music", "Music", "/Music 02.png");
            Category cat2 = new Category(null, "Gaming", "Gaming", "/Electronics 01.png");
            Category cat3 = new Category(null, "Reading", "Reading", "/Image.png");
            Category cat4 = new Category(null, "Fashion", "Fashion", "/Image.png");
            categoryRepo.save(cat1);
            categoryRepo.save(cat2);
            categoryRepo.save(cat3);
            categoryRepo.save(cat4);

            Product prod1 = new Product(null, "\"Awaken, My Love!\"", "Childish Gambino Album", "/Music 02.png", 39.99, true, true, cat1);
            Product prod2 = new Product(null, "Dark Lane Demo Tapes", "Drake Mixtape", "/Music 03.png", 32.99, true, true, cat1);
            Product prod3 = new Product(null, "4 Your Eyez Only", "J. Cole Album", "/Music 04.png", 28.99, true, true, cat1);
            
            Product prod4 = new Product(null, "Oculus Rift VR Headset", "Virtual reality headset", "/Electronics 02.png", 299.99, true, true, cat2);
            Product prod5 = new Product(null, "Dell 49\" Ultrawide Screen", "Ultrawide screen monitor", "/Electronics 01.png", 1199.99, true, true, cat2);
            Product prod6 = new Product(null, "Xbox One Elite Series 2 Controller", "Elite gaming controller", "/Image.png", 79.99, true, true, cat2);
            
            productRepo.save(prod1);
            productRepo.save(prod2);
            productRepo.save(prod3);
            productRepo.save(prod4);
            productRepo.save(prod5);
            productRepo.save(prod6);

            if (dealRepo.count() == 0) {
                Deal deal1 = new Deal(null, prod1, 19.99, true);
                Deal deal2 = new Deal(null, prod6, 59.99, true);
                dealRepo.save(deal1);
                dealRepo.save(deal2);
            }
        }
    }
}
