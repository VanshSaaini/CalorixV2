package com.calorix.backend.service.impl;

import com.calorix.backend.dto.bmi.BmiRecordRequest;
import com.calorix.backend.dto.bmi.BmiRecordResponse;
import com.calorix.backend.entity.BmiRecord;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.BmiRecordMapper;
import com.calorix.backend.repository.BmiRecordRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.BmiRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BmiRecordServiceImpl implements BmiRecordService {

        private final BmiRecordRepository bmiRecordRepository;
        private final UserRepository userRepository;
        private final BmiRecordMapper bmiRecordMapper;

        @Override
        public BmiRecordResponse saveBmi(Long userId, BmiRecordRequest request) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with id: " + userId));

                BmiRecord bmiRecord = bmiRecordMapper.toEntity(request);
                bmiRecord.setUser(user);

                BmiRecord saved = bmiRecordRepository.save(bmiRecord);

                return bmiRecordMapper.toResponse(saved);
        }

        @Override
        public BmiRecordResponse updateBmi(Long id, BmiRecordRequest request) {

                BmiRecord bmiRecord = bmiRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMI record not found with id: " + id));

                bmiRecord.setWeight(request.getWeight());
                bmiRecord.setHeight(request.getHeight());
                bmiRecord.setBmi(request.getBmi());
                bmiRecord.setCategory(request.getCategory());
                bmiRecord.setRecordDate(request.getRecordDate());

                BmiRecord updated = bmiRecordRepository.save(bmiRecord);

                return bmiRecordMapper.toResponse(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public BmiRecordResponse getBmi(Long id) {

                BmiRecord bmiRecord = bmiRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMI record not found with id: " + id));

                return bmiRecordMapper.toResponse(bmiRecord);
        }

        @Override
        @Transactional(readOnly = true)
        public List<BmiRecordResponse> getUserBmiRecords(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bmiRecordRepository.findByUserIdOrderByRecordDateDesc(userId)
                                .stream()
                                .map(bmiRecordMapper::toResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public BmiRecordResponse getLatestBmi(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bmiRecordRepository
                                .findTopByUserIdOrderByRecordDateDesc(userId)
                                .map(bmiRecordMapper::toResponse)
                                .orElse(null);
        }

        @Override
        public void deleteBmi(Long id) {

                BmiRecord bmiRecord = bmiRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMI record not found with id: " + id));

                bmiRecordRepository.delete(bmiRecord);
        }
}