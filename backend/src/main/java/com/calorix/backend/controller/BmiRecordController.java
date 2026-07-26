package com.calorix.backend.controller;

import com.calorix.backend.dto.bmi.BmiRecordRequest;
import com.calorix.backend.dto.bmi.BmiRecordResponse;
import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.service.BmiRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bmi")
@RequiredArgsConstructor
public class BmiRecordController {

    private final BmiRecordService bmiRecordService;

    /**
     * Save BMI Record
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<BmiRecordResponse> saveBmiRecord(
            @PathVariable Long userId,
            @Valid @RequestBody BmiRecordRequest request) {

        BmiRecordResponse response =
                bmiRecordService.saveBmi(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update BMI Record
     */
    @PutMapping("/{id}")
    public ResponseEntity<BmiRecordResponse> updateBmiRecord(
            @PathVariable Long id,
            @Valid @RequestBody BmiRecordRequest request) {

        return ResponseEntity.ok(
                bmiRecordService.updateBmi(id, request)
        );
    }

    /**
     * Get BMI Record By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<BmiRecordResponse> getBmiRecord(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bmiRecordService.getBmi(id)
        );
    }

    /**
     * Get All BMI Records Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BmiRecordResponse>> getUserBmiRecords(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bmiRecordService.getUserBmiRecords(userId)
        );
    }

    /**
     * Get Latest BMI Record
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<BmiRecordResponse> getLatestBmi(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bmiRecordService.getLatestBmi(userId)
        );
    }

    /**
     * Delete BMI Record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBmiRecord(
            @PathVariable Long id) {

        bmiRecordService.deleteBmi(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("BMI record deleted successfully.")
                        .build()
        );
    }
}