package com.calorix.backend.service.impl;

import com.calorix.backend.dto.goal.GoalRequest;
import com.calorix.backend.dto.goal.GoalResponse;
import com.calorix.backend.entity.Goal;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.GoalMapper;
import com.calorix.backend.repository.GoalRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final GoalMapper goalMapper;

    @Override
    public GoalResponse createGoal(Long userId, GoalRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        Goal goal = goalMapper.toEntity(request);
        goal.setUser(user);

        Goal savedGoal = goalRepository.save(goal);

        return goalMapper.toResponse(savedGoal);
    }

    @Override
    public GoalResponse updateGoal(Long id, GoalRequest request) {

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found with id: " + id));

        goal.setGoalType(request.getGoalType());
        goal.setTargetWeight(request.getTargetWeight());
        goal.setTargetCalories(request.getTargetCalories());
        goal.setWeeklyTarget(request.getWeeklyTarget());
        goal.setStartDate(request.getStartDate());
        goal.setTargetDate(request.getTargetDate());
        goal.setCompleted(request.getCompleted());

        Goal updatedGoal = goalRepository.save(goal);

        return goalMapper.toResponse(updatedGoal);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalResponse getGoal(Long id) {

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found with id: " + id));

        return goalMapper.toResponse(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalResponse> getUserGoals(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return goalRepository.findByUserId(userId)
                .stream()
                .map(goalMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteGoal(Long id) {

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found with id: " + id));

        goalRepository.delete(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalResponse getActiveGoal(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return goalRepository
                .findFirstByUserIdAndCompletedFalse(userId)
                .map(goalMapper::toResponse)
                .orElse(null);
    }

    @Override
    public void markGoalCompleted(Long goalId) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Goal not found with id: " + goalId));

        goal.setCompleted(true);

        goalRepository.save(goal);
    }

}