package com.example.habitual_backend.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    private String imageURL;
    
    @Column(nullable = false)
    private double price;
    
    private boolean trending;
    
    private boolean popular;

    // Relationship field here

@ManyToOne
@JoinColumn(name = "category_id")
private Category category;
    
}
