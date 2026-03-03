package com.example.habitual_backend.auth.auth_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.habitual_backend.auth.dto.AuthResponse;
import com.example.habitual_backend.auth.dto.LoginRequest;
import com.example.habitual_backend.auth.dto.RegisterRequest;
import com.example.habitual_backend.auth.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService service;
    
    public AuthController(UserService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req){
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){
        return ResponseEntity.ok(service.login(req));
    }
    
    
}
