package com.calorix.backend.service;

import com.calorix.backend.dto.auth.JwtResponse;
import com.calorix.backend.dto.auth.LoginRequest;
import com.calorix.backend.dto.auth.RefreshTokenRequest;
import com.calorix.backend.dto.auth.RegisterRequest;

public interface AuthService {

    JwtResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

    JwtResponse refreshToken(RefreshTokenRequest request);

}