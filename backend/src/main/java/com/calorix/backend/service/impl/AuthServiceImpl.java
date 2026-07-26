package com.calorix.backend.service.impl;

import com.calorix.backend.dto.auth.JwtResponse;
import com.calorix.backend.dto.auth.LoginRequest;
import com.calorix.backend.dto.auth.RefreshTokenRequest;
import com.calorix.backend.dto.auth.RegisterRequest;
import com.calorix.backend.entity.Role;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.BadRequestException;
import com.calorix.backend.exception.EmailAlreadyExistsException;
import com.calorix.backend.repository.RoleRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.security.jwt.JwtService;
import com.calorix.backend.security.userdetails.CustomUserDetails;
import com.calorix.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    /**
     * Register New User
     */
    @Override
    public JwtResponse register(RegisterRequest request) {

        // Check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered.");
        }

        // Default Role
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BadRequestException(
                        "Default role ROLE_USER not found."));

        // Create User
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        user.setAge(request.getAge());
        user.setHeight(request.getHeight());
        user.setGender(request.getGender());

        user.setRole(role);

        // Change to false if implementing email verification
        user.setEmailVerified(true);

        User savedUser = userRepository.save(user);

        // Generate JWT
        CustomUserDetails userDetails = new CustomUserDetails(savedUser);

        String accessToken = jwtService.generateToken(userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildJwtResponse(savedUser, accessToken, refreshToken);
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildJwtResponse(userDetails.getUserEntity(), accessToken, refreshToken);
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new BadRequestException("Invalid refresh token.");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("User not found."));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new BadRequestException("Refresh token has expired or is invalid.");
        }

        String newAccessToken = jwtService.generateToken(userDetails);

        return buildJwtResponse(user, newAccessToken, refreshToken);
    }

    private JwtResponse buildJwtResponse(User user, String accessToken, String refreshToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole() == null ? null : user.getRole().getName())
                .build();
    }
}
