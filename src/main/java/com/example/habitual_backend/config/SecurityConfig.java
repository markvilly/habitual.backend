package com.example.habitual_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))
      .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
        .anyRequest().permitAll()
      )
      .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}