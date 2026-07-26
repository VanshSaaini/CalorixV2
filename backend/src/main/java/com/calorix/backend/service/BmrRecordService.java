package com.calorix.backend.service;

import com.calorix.backend.dto.bmr.BmrRecordRequest;
import com.calorix.backend.dto.bmr.BmrRecordResponse;

import java.util.List;

public interface BmrRecordService {

    /**
     * Save a new BMR record for a user.
     */
    BmrRecordResponse saveBmr(Long userId, BmrRecordRequest request);

    /**
     * Update an existing BMR record.
     */
    BmrRecordResponse updateBmr(Long recordId, BmrRecordRequest request);

    /**
     * Get a BMR record by its ID.
     */
    BmrRecordResponse getBmr(Long recordId);

    /**
     * Get all BMR records for a user.
     */
    List<BmrRecordResponse> getUserBmrRecords(Long userId);

    /**
     * Get the latest BMR record for a user.
     */
    BmrRecordResponse getLatestBmr(Long userId);

    /**
     * Delete a BMR record.
     */
    void deleteBmr(Long recordId);
}