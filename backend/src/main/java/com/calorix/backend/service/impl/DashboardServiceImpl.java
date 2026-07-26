package com.calorix.backend.service.impl;

import com.calorix.backend.dto.bmi.BmiRecordResponse;
import com.calorix.backend.dto.bmr.BmrRecordResponse;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;
import com.calorix.backend.dto.dashboard.DashboardResponse;
import com.calorix.backend.dto.goal.GoalResponse;
import com.calorix.backend.dto.macro.MacroRecordResponse;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;
import com.calorix.backend.dto.water.WaterIntakeResponse;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.UserMapper;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(
    readOnly = true,
    noRollbackFor = ResourceNotFoundException.class
)
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final WeightRecordService weightRecordService;
    private final BodyMeasurementService bodyMeasurementService;
    private final BmiRecordService bmiRecordService;
    private final BmrRecordService bmrRecordService;
    private final MacroRecordService macroRecordService;
    private final DailyCaloriesService dailyCaloriesService;
    private final WaterIntakeService waterIntakeService;
    private final GoalService goalService;
    private final ProgressPhotoService progressPhotoService;

    @Override
    public DashboardResponse getDashboard(String email) {

        User user = getUser(email);
        Long userId = user.getId();

        return DashboardResponse.builder()
                .user(userMapper.toResponse(user))
                .latestWeight(safeWeight(userId))
                .latestMeasurement(safeMeasurement(userId))
                .latestBmi(safeBmi(userId))
                .latestBmr(safeBmr(userId))
                .latestMacros(safeMacros(userId))
                .latestCalories(safeCalories(userId))
                .latestWater(safeWater(userId))
                .activeGoal(safeGoal(userId))
                .latestPhoto(safePhoto(userId))
                .build();
    }

    @Override
    public DashboardResponse getDashboardSummary(String email) {

        User user = getUser(email);
        Long userId = user.getId();

        return DashboardResponse.builder()
                .user(userMapper.toResponse(user))
                .latestWeight(safeWeight(userId))
                .latestBmi(safeBmi(userId))
                .latestCalories(safeCalories(userId))
                .latestWater(safeWater(userId))
                .activeGoal(safeGoal(userId))
                .build();
    }

    @Override
    public DashboardResponse getTodayDashboard(String email) {

        User user = getUser(email);
        Long userId = user.getId();

        return DashboardResponse.builder()
                .user(userMapper.toResponse(user))
                .latestWeight(safeWeight(userId))
                .latestCalories(safeCalories(userId))
                .latestWater(safeWater(userId))
                .latestMacros(safeMacros(userId))
                .build();
    }

    /**
     * Helper Method
     */
    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));
    }

    /* ================= Safe Helper Methods ================= */

    private WeightRecordResponse safeWeight(Long userId) {
        try {
            return weightRecordService.getLatestWeight(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private BodyMeasurementResponse safeMeasurement(Long userId) {
        try {
            return bodyMeasurementService.getLatestMeasurement(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private BmiRecordResponse safeBmi(Long userId) {
        try {
            return bmiRecordService.getLatestBmi(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private BmrRecordResponse safeBmr(Long userId) {
        try {
            return bmrRecordService.getLatestBmr(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private MacroRecordResponse safeMacros(Long userId) {
        try {
            return macroRecordService.getLatestMacros(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private DailyCaloriesResponse safeCalories(Long userId) {
        try {
            return dailyCaloriesService.getLatestCalories(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private WaterIntakeResponse safeWater(Long userId) {
        try {
            return waterIntakeService.getLatestWater(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private GoalResponse safeGoal(Long userId) {
        try {
            return goalService.getActiveGoal(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    private ProgressPhotoResponse safePhoto(Long userId) {
        try {
            return progressPhotoService.getLatestPhoto(userId);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}