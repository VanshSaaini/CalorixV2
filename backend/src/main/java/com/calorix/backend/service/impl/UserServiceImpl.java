package com.calorix.backend.service.impl;

import com.calorix.backend.dto.user.UserRequest;
import com.calorix.backend.dto.user.UserResponse;
import com.calorix.backend.dto.user.UserUpdateRequest;
import com.calorix.backend.entity.Role;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.EmailAlreadyExistsException;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.UserMapper;
import com.calorix.backend.repository.RoleRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.security.userdetails.CustomUserDetails;
import com.calorix.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists: " + request.getEmail());
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found"));

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailVerified(false);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        updateUserFields(existingUser, request);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {

        return userMapper.toResponse(getAuthenticatedUser());
    }

    @Override
    public UserResponse updateCurrentUser(UserUpdateRequest request) {

        User user = getAuthenticatedUser();

        updateUserFields(user, request);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteCurrentUser() {

        User user = getAuthenticatedUser();

        userRepository.delete(user);
    }

    /**
     * Get currently authenticated user.
     */
    private User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("No authenticated user found.");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new ResourceNotFoundException("Invalid authenticated user.");
        }

        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
    }

    /**
     * Update common user fields.
     */
    private void updateUserFields(User user, UserUpdateRequest request) {

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already exists: " + request.getEmail());
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setHeight(request.getHeight());
        user.setGender(request.getGender().name());
    }
}