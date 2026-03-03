package com.example.habitual_backend.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.habitual_backend.auth.dto.AuthResponse;
import com.example.habitual_backend.auth.dto.LoginRequest;
import com.example.habitual_backend.auth.dto.RegisterRequest;
import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest req){
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            return new AuthResponse("", 0L, "", "Email already exists");
        }
        User user = new User(req.getName(), req.getEmail(), req.getPassword(), LocalDateTime.now());
        userRepository.save(user);
        return new AuthResponse(user.getName(), user.getId(), user.getEmail(), "User registered successfully");

    }

    public AuthResponse login(LoginRequest req){
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if(user.isEmpty()){
            return new AuthResponse("", 0L, "", "User not found");
        }

        if(!user.get().getPassword().equals(req.getPassword())){
            return new AuthResponse("", 0L, "", "Invalid password");
        }

        return new AuthResponse(user.get().getName(), user.get().getId(), user.get().getEmail(), "Login successful");
    }


}
