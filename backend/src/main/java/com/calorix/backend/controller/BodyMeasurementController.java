package com.calorix.backend.controller;

import com.calorix.backend.dto.bodymeasurement.BodyMeasurementRequest;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;
import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.service.BodyMeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/body-measurements")
@RequiredArgsConstructor
public class BodyMeasurementController {

    private final BodyMeasurementService bodyMeasurementService;

    /**
     * Save Body Measurement
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<BodyMeasurementResponse> saveBodyMeasurement(
            @PathVariable Long userId,
            @Valid @RequestBody BodyMeasurementRequest request) {

        BodyMeasurementResponse response =
                bodyMeasurementService.saveMeasurement(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Body Measurement
     */
    @PutMapping("/{id}")
    public ResponseEntity<BodyMeasurementResponse> updateBodyMeasurement(
            @PathVariable Long id,
            @Valid @RequestBody BodyMeasurementRequest request) {

        return ResponseEntity.ok(
                bodyMeasurementService.updateMeasurement(id, request)
        );
    }

    /**
     * Get Body Measurement By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<BodyMeasurementResponse> getBodyMeasurement(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bodyMeasurementService.getMeasurement(id)
        );
    }

    /**
     * Get All Body Measurements Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BodyMeasurementResponse>> getUserMeasurements(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bodyMeasurementService.getUserMeasurements(userId)
        );
    }

    /**
     * Get Latest Body Measurement
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<BodyMeasurementResponse> getLatestMeasurement(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bodyMeasurementService.getLatestMeasurement(userId)
        );
    }

    /**
     * Delete Body Measurement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBodyMeasurement(
            @PathVariable Long id) {

        bodyMeasurementService.deleteMeasurement(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Body measurement deleted successfully.")
                        .build()
        );
    }
}