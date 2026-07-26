package com.calorix.backend.controller;

import com.calorix.backend.dto.calories.DailyCaloriesRequest;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;
import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.service.DailyCaloriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calories")
@RequiredArgsConstructor
public class DailyCaloriesController {

    private final DailyCaloriesService dailyCaloriesService;

    /**
     * Save Daily Calories
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<DailyCaloriesResponse> saveCalories(
            @PathVariable Long userId,
            @Valid @RequestBody DailyCaloriesRequest request) {

        DailyCaloriesResponse response =
                dailyCaloriesService.saveCalories(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Daily Calories
     */
    @PutMapping("/{id}")
    public ResponseEntity<DailyCaloriesResponse> updateCalories(
            @PathVariable Long id,
            @Valid @RequestBody DailyCaloriesRequest request) {

        return ResponseEntity.ok(
                dailyCaloriesService.updateCalories(id, request)
        );
    }

    /**
     * Get Daily Calories By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<DailyCaloriesResponse> getCalories(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                dailyCaloriesService.getCalories(id)
        );
    }

    /**
     * Get All Daily Calories Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DailyCaloriesResponse>> getUserCalories(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dailyCaloriesService.getUserCalories(userId)
        );
    }

    /**
     * Get Latest Daily Calories
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<DailyCaloriesResponse> getLatestCalories(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dailyCaloriesService.getLatestCalories(userId)
        );
    }

    /**
     * Delete Daily Calories
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCalories(
            @PathVariable Long id) {

        dailyCaloriesService.deleteCalories(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Daily calories record deleted successfully.")
                        .build()
        );
    }
}