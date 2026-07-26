package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.goal.GoalRequest;
import com.calorix.backend.dto.goal.GoalResponse;
import com.calorix.backend.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    /**
     * Create Goal
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<GoalResponse> createGoal(
            @PathVariable Long userId,
            @Valid @RequestBody GoalRequest request) {

        GoalResponse response = goalService.createGoal(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Goal
     */
    @PutMapping("/{id}")
    public ResponseEntity<GoalResponse> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalRequest request) {

        return ResponseEntity.ok(
                goalService.updateGoal(id, request)
        );
    }

    /**
     * Get Goal By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GoalResponse> getGoal(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                goalService.getGoal(id)
        );
    }

    /**
     * Get User Goals
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GoalResponse>> getUserGoals(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                goalService.getUserGoals(userId)
        );
    }

    /**
     * Get Active Goal
     */
    @GetMapping("/active/{userId}")
    public ResponseEntity<GoalResponse> getActiveGoal(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                goalService.getActiveGoal(userId)
        );
    }

    /**
     * Mark Goal Completed
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse> completeGoal(
            @PathVariable Long id) {

        goalService.markGoalCompleted(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Goal marked as completed.")
                        .build()
        );
    }

    /**
     * Delete Goal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteGoal(
            @PathVariable Long id) {

        goalService.deleteGoal(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Goal deleted successfully.")
                        .build()
        );
    }
}