package com.calorix.backend.service.impl;

import com.calorix.backend.dto.calories.DailyCaloriesRequest;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;
import com.calorix.backend.entity.DailyCalories;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.DailyCaloriesMapper;
import com.calorix.backend.repository.DailyCaloriesRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.DailyCaloriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyCaloriesServiceImpl implements DailyCaloriesService {

    private final DailyCaloriesRepository dailyCaloriesRepository;
    private final UserRepository userRepository;
    private final DailyCaloriesMapper dailyCaloriesMapper;

    @Override
    public DailyCaloriesResponse saveCalories(Long userId,
            DailyCaloriesRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        DailyCalories dailyCalories = dailyCaloriesMapper.toEntity(request);
        dailyCalories.setUser(user);

        DailyCalories saved = dailyCaloriesRepository.save(dailyCalories);

        return dailyCaloriesMapper.toResponse(saved);
    }

    @Override
    public DailyCaloriesResponse updateCalories(Long id,
            DailyCaloriesRequest request) {

        DailyCalories dailyCalories = dailyCaloriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Daily calorie record not found with id: " + id));

        dailyCalories.setConsumedCalories(request.getConsumedCalories());
        dailyCalories.setBurnedCalories(request.getBurnedCalories());
        dailyCalories.setRemainingCalories(request.getRemainingCalories());
        dailyCalories.setRecordDate(request.getRecordDate());

        DailyCalories updated = dailyCaloriesRepository.save(dailyCalories);

        return dailyCaloriesMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyCaloriesResponse getCalories(Long id) {

        DailyCalories dailyCalories = dailyCaloriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Daily calorie record not found with id: " + id));

        return dailyCaloriesMapper.toResponse(dailyCalories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyCaloriesResponse> getUserCalories(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return dailyCaloriesRepository
                .findByUserIdOrderByRecordDateDesc(userId)
                .stream()
                .map(dailyCaloriesMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCalories(Long id) {

        DailyCalories dailyCalories = dailyCaloriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Daily calorie record not found with id: " + id));

        dailyCaloriesRepository.delete(dailyCalories);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyCaloriesResponse getLatestCalories(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return dailyCaloriesRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .map(dailyCaloriesMapper::toResponse)
                .orElse(null);
    }
}