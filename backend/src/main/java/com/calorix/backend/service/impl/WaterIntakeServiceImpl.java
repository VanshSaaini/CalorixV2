package com.calorix.backend.service.impl;

import com.calorix.backend.dto.water.WaterIntakeRequest;
import com.calorix.backend.dto.water.WaterIntakeResponse;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import com.calorix.backend.entity.User;
import com.calorix.backend.entity.WaterIntake;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.WaterIntakeMapper;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.repository.WaterIntakeRepository;
import com.calorix.backend.service.WaterIntakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WaterIntakeServiceImpl implements WaterIntakeService {

    private final WaterIntakeRepository waterIntakeRepository;
    private final UserRepository userRepository;
    private final WaterIntakeMapper waterIntakeMapper;

    @Override
    public WaterIntakeResponse saveWater(Long userId,
            WaterIntakeRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        WaterIntake waterIntake = waterIntakeMapper.toEntity(request);
        waterIntake.setUser(user);

        WaterIntake saved = waterIntakeRepository.save(waterIntake);

        return waterIntakeMapper.toResponse(saved);
    }

    @Override
    public WaterIntakeResponse updateWater(Long id,
            WaterIntakeRequest request) {

        WaterIntake waterIntake = waterIntakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Water intake record not found with id: " + id));

        waterIntake.setLitres(request.getLitres());
        waterIntake.setRecordDate(request.getRecordDate());

        WaterIntake updated = waterIntakeRepository.save(waterIntake);

        return waterIntakeMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public WaterIntakeResponse getWater(Long id) {

        WaterIntake waterIntake = waterIntakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Water intake record not found with id: " + id));

        return waterIntakeMapper.toResponse(waterIntake);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WaterIntakeResponse> getUserWater(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return waterIntakeRepository
                .findByUserIdOrderByRecordDateDesc(userId)
                .stream()
                .map(waterIntakeMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteWater(Long id) {

        WaterIntake waterIntake = waterIntakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Water intake record not found with id: " + id));

        waterIntakeRepository.delete(waterIntake);
    }

    @Override
    @Transactional(readOnly = true)
    public WaterIntakeResponse getLatestWater(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + userId);
        }

        return waterIntakeRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .map(waterIntakeMapper::toResponse)
                .orElse(null);
    }
}