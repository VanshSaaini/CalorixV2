package com.calorix.backend.service;

import com.calorix.backend.dto.user.UserRequest;
import com.calorix.backend.dto.user.UserResponse;
import com.calorix.backend.dto.user.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);

    /**
     * Current authenticated user
     */
    UserResponse getCurrentUser();

    /**
     * Update authenticated user
     */
    UserResponse updateCurrentUser(UserUpdateRequest request);

    /**
     * Delete authenticated user
     */
    void deleteCurrentUser();
}