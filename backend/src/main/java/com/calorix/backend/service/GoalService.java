package com.calorix.backend.service;

import com.calorix.backend.dto.goal.GoalRequest;
import com.calorix.backend.dto.goal.GoalResponse;

import java.util.List;

public interface GoalService {

    GoalResponse createGoal(Long userId, GoalRequest request);

    GoalResponse updateGoal(Long id, GoalRequest request);

    GoalResponse getGoal(Long id);

    List<GoalResponse> getUserGoals(Long userId);

    GoalResponse getActiveGoal(Long userId);

    void markGoalCompleted(Long goalId);

    void deleteGoal(Long id);
}