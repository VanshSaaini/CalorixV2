package com.calorix.backend.service;

import com.calorix.backend.dto.weight.WeightRecordRequest;
import com.calorix.backend.dto.weight.WeightRecordResponse;

import java.util.List;

public interface WeightRecordService {

    WeightRecordResponse saveWeight(Long userId, WeightRecordRequest request);

    WeightRecordResponse updateWeight(Long id, WeightRecordRequest request);

    WeightRecordResponse getWeight(Long id);

    WeightRecordResponse getLatestWeight(Long userId);

    Double getCurrentWeight(Long userId);

    List<WeightRecordResponse> getUserWeights(Long userId);

    void deleteWeight(Long id);

}