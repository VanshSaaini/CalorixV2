package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.weight.WeightRecordRequest;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import com.calorix.backend.service.WeightRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weights")
@RequiredArgsConstructor
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    /**
     * Save Weight Record
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<WeightRecordResponse> saveWeightRecord(
            @PathVariable Long userId,
            @Valid @RequestBody WeightRecordRequest request) {

        WeightRecordResponse response =
                weightRecordService.saveWeight(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Weight Record
     */
    @PutMapping("/{id}")
    public ResponseEntity<WeightRecordResponse> updateWeightRecord(
            @PathVariable Long id,
            @Valid @RequestBody WeightRecordRequest request) {

        return ResponseEntity.ok(
                weightRecordService.updateWeight(id, request)
        );
    }

    /**
     * Get Weight Record By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<WeightRecordResponse> getWeightRecord(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                weightRecordService.getWeight(id)
        );
    }

    /**
     * Get All Weight Records Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WeightRecordResponse>> getUserWeights(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                weightRecordService.getUserWeights(userId)
        );
    }

    /**
     * Get Latest Weight Record
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<WeightRecordResponse> getLatestWeight(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                weightRecordService.getLatestWeight(userId)
        );
    }

    /**
     * Delete Weight Record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWeightRecord(
            @PathVariable Long id) {

        weightRecordService.deleteWeight(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Weight record deleted successfully.")
                        .build()
        );
    }
}