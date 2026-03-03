package com.example.habitual_backend.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.habitual_backend.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
