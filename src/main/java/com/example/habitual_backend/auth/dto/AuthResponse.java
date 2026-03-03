package com.example.habitual_backend.auth.dto;

public class AuthResponse {
    private String name;
    private Long id;
    String email;
    String message;

    public AuthResponse(){}
    public AuthResponse(String name, Long id, String email, String message){
        this.name = name;
        this.id = id;
        this.email = email;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
