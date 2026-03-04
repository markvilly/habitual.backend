package com.example.habitual_backend.profile.controller;

import com.example.habitual_backend.profile.dto.ProfileResponse;
import com.example.habitual_backend.profile.dto.UpdateInterestsRequest;
import com.example.habitual_backend.profile.dto.UpdateProfileRequest;
import com.example.habitual_backend.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@RequestParam Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @RequestParam Long userId,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }

    @PutMapping("/interests")
    public ResponseEntity<ProfileResponse> updateInterests(
            @RequestParam Long userId,
            @RequestBody UpdateInterestsRequest request) {
        return ResponseEntity.ok(profileService.updateInterests(userId, request));
    }
}
