package com.calorix.backend.controller;

import com.calorix.backend.dto.auth.JwtResponse;
import com.calorix.backend.dto.auth.LoginRequest;
import com.calorix.backend.dto.auth.RefreshTokenRequest;
import com.calorix.backend.dto.auth.RegisterRequest;

import com.calorix.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        JwtResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Login user
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    /**
     * Generate new access token using refresh token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }


    /**
     * Email verification (optional)
     */
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        // Implement later
        return ResponseEntity.ok(
                "Email verified successfully."
        );
    }
}