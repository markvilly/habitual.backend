package com.example.habitual_backend.profile.service;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.auth.repo.UserRepository;
import com.example.habitual_backend.profile.dto.ProfileResponse;
import com.example.habitual_backend.profile.dto.UpdateInterestsRequest;
import com.example.habitual_backend.profile.dto.UpdateProfileRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return buildProfileResponse(user);
    }

    public ProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        userRepository.save(user);
        return buildProfileResponse(user);
    }

    public ProfileResponse updateInterests(Long userId, UpdateInterestsRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getInterests() != null) {
            user.setInterests(String.join(",", request.getInterests()));
        }

        userRepository.save(user);
        return buildProfileResponse(user);
    }

    private ProfileResponse buildProfileResponse(User user) {
        List<String> interests = Collections.emptyList();
        if (user.getInterests() != null && !user.getInterests().isBlank()) {
            interests = Arrays.stream(user.getInterests().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                interests,
                user.getCreatedAt()
        );
    }
}
