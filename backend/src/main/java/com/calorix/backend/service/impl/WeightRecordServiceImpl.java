package com.calorix.backend.service.impl;

import com.calorix.backend.dto.weight.WeightRecordRequest;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import com.calorix.backend.entity.User;
import com.calorix.backend.entity.WeightRecord;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.WeightRecordMapper;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.repository.WeightRecordRepository;
import com.calorix.backend.service.WeightRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WeightRecordServiceImpl implements WeightRecordService {

    private final WeightRecordRepository weightRecordRepository;
    private final UserRepository userRepository;
    private final WeightRecordMapper weightRecordMapper;

    @Override
    public WeightRecordResponse saveWeight(Long userId, WeightRecordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        WeightRecord weightRecord = weightRecordMapper.toEntity(request);
        weightRecord.setUser(user);

        return weightRecordMapper.toResponse(
                weightRecordRepository.save(weightRecord));
    }

    @Override
    public WeightRecordResponse updateWeight(Long id, WeightRecordRequest request) {

        WeightRecord weightRecord = weightRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight record not found with id: " + id));

        weightRecord.setWeight(request.getWeight());
        weightRecord.setRecordDate(request.getRecordDate());

        return weightRecordMapper.toResponse(
                weightRecordRepository.save(weightRecord));
    }

    @Override
    @Transactional(readOnly = true)
    public WeightRecordResponse getWeight(Long id) {

        WeightRecord weightRecord = weightRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight record not found with id: " + id));

        return weightRecordMapper.toResponse(weightRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeightRecordResponse> getUserWeights(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return weightRecordRepository.findByUserIdOrderByRecordDateDesc(userId)
                .stream()
                .map(weightRecordMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WeightRecordResponse getLatestWeight(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return weightRecordRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .map(weightRecordMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getCurrentWeight(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        WeightRecord weightRecord = weightRecordRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No weight record found for user id: " + userId));

        return weightRecord.getWeight();
    }

    @Override
    public void deleteWeight(Long id) {

        WeightRecord weightRecord = weightRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight record not found with id: " + id));

        weightRecordRepository.delete(weightRecord);
    }
}