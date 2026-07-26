package com.calorix.backend.service;

import com.calorix.backend.dto.bmi.BmiRecordRequest;
import com.calorix.backend.dto.bmi.BmiRecordResponse;

import java.util.List;

public interface BmiRecordService {

    BmiRecordResponse saveBmi(Long userId, BmiRecordRequest request);

    BmiRecordResponse updateBmi(Long id, BmiRecordRequest request);

    BmiRecordResponse getBmi(Long id);

    List<BmiRecordResponse> getUserBmiRecords(Long userId);

    BmiRecordResponse getLatestBmi(Long userId);

    void deleteBmi(Long id);
}