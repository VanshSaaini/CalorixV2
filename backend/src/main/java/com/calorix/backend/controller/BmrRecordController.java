package com.calorix.backend.controller;

import com.calorix.backend.dto.bmr.BmrRecordRequest;
import com.calorix.backend.dto.bmr.BmrRecordResponse;
import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.service.BmrRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bmr")
@RequiredArgsConstructor
public class BmrRecordController {

    private final BmrRecordService bmrRecordService;

    /**
     * Save BMR Record
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<BmrRecordResponse> saveBmrRecord(
            @PathVariable Long userId,
            @Valid @RequestBody BmrRecordRequest request) {

        BmrRecordResponse response =
                bmrRecordService.saveBmr(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update BMR Record
     */
    @PutMapping("/{id}")
    public ResponseEntity<BmrRecordResponse> updateBmrRecord(
            @PathVariable Long id,
            @Valid @RequestBody BmrRecordRequest request) {

        return ResponseEntity.ok(
                bmrRecordService.updateBmr(id, request)
        );
    }

    /**
     * Get BMR Record By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<BmrRecordResponse> getBmrRecord(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bmrRecordService.getBmr(id)
        );
    }

    /**
     * Get All BMR Records Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BmrRecordResponse>> getUserBmrRecords(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bmrRecordService.getUserBmrRecords(userId)
        );
    }

    /**
     * Get Latest BMR Record
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<BmrRecordResponse> getLatestBmr(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bmrRecordService.getLatestBmr(userId)
        );
    }

    /**
     * Delete BMR Record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBmrRecord(
            @PathVariable Long id) {

        bmrRecordService.deleteBmr(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("BMR record deleted successfully.")
                        .build()
        );
    }
}