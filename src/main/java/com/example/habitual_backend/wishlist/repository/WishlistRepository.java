package com.example.habitual_backend.wishlist.repository;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.product.model.Product;
import com.example.habitual_backend.wishlist.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndProduct(User user, Product product);
}
