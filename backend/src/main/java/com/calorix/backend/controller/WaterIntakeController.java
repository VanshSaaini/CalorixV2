package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.water.WaterIntakeRequest;
import com.calorix.backend.dto.water.WaterIntakeResponse;
import com.calorix.backend.service.WaterIntakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water")
@RequiredArgsConstructor
public class WaterIntakeController {

    private final WaterIntakeService waterIntakeService;

    /**
     * Save Water Intake
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<WaterIntakeResponse> saveWaterIntake(
            @PathVariable Long userId,
            @Valid @RequestBody WaterIntakeRequest request) {

        WaterIntakeResponse response =
                waterIntakeService.saveWater(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Water Intake
     */
    @PutMapping("/{id}")
    public ResponseEntity<WaterIntakeResponse> updateWaterIntake(
            @PathVariable Long id,
            @Valid @RequestBody WaterIntakeRequest request) {

        return ResponseEntity.ok(
                waterIntakeService.updateWater(id, request)
        );
    }

    /**
     * Get Water Intake By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<WaterIntakeResponse> getWaterIntake(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                waterIntakeService.getWater(id)
        );
    }

    /**
     * Get User Water Intake Records
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WaterIntakeResponse>> getUserWaterIntake(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                waterIntakeService.getUserWater(userId)
        );
    }

    /**
     * Get Latest Water Intake
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<WaterIntakeResponse> getLatestWaterIntake(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                waterIntakeService.getLatestWater(userId)
        );
    }

    /**
     * Delete Water Intake
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWaterIntake(
            @PathVariable Long id) {

        waterIntakeService.deleteWater(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Water intake record deleted successfully.")
                        .build()
        );
    }
}