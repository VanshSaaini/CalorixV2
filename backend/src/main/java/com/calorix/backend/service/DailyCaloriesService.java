package com.calorix.backend.service;

import com.calorix.backend.dto.calories.DailyCaloriesRequest;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;

import java.util.List;

public interface DailyCaloriesService {

    DailyCaloriesResponse saveCalories(Long userId, DailyCaloriesRequest request);

    DailyCaloriesResponse updateCalories(Long id, DailyCaloriesRequest request);

    DailyCaloriesResponse getCalories(Long id);

    List<DailyCaloriesResponse> getUserCalories(Long userId);

    DailyCaloriesResponse getLatestCalories(Long userId);

    void deleteCalories(Long id);
}