package com.calorix.backend.service;

import com.calorix.backend.dto.water.WaterIntakeRequest;
import com.calorix.backend.dto.water.WaterIntakeResponse;

import java.util.List;

public interface WaterIntakeService {

    WaterIntakeResponse saveWater(Long userId, WaterIntakeRequest request);

    WaterIntakeResponse updateWater(Long id, WaterIntakeRequest request);

    WaterIntakeResponse getWater(Long id);

    List<WaterIntakeResponse> getUserWater(Long userId);

    void deleteWater(Long id);

    WaterIntakeResponse getLatestWater(Long userId);
}