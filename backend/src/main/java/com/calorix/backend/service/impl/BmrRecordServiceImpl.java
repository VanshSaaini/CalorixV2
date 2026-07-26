package com.calorix.backend.service.impl;

import com.calorix.backend.dto.bmr.BmrRecordRequest;
import com.calorix.backend.dto.bmr.BmrRecordResponse;
import com.calorix.backend.entity.BmrRecord;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.BmrRecordMapper;
import com.calorix.backend.repository.BmrRecordRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.BmrRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BmrRecordServiceImpl implements BmrRecordService {

        private final BmrRecordRepository bmrRecordRepository;
        private final UserRepository userRepository;
        private final BmrRecordMapper bmrRecordMapper;

        @Override
        public BmrRecordResponse saveBmr(Long userId, BmrRecordRequest request) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with id: " + userId));

                BmrRecord bmrRecord = bmrRecordMapper.toEntity(request);
                bmrRecord.setUser(user);

                BmrRecord saved = bmrRecordRepository.save(bmrRecord);

                return bmrRecordMapper.toResponse(saved);
        }

        @Override
        public BmrRecordResponse updateBmr(Long id, BmrRecordRequest request) {

                BmrRecord bmrRecord = bmrRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMR record not found with id: " + id));

                bmrRecord.setAge(request.getAge());
                bmrRecord.setWeight(request.getWeight());
                bmrRecord.setHeight(request.getHeight());
                bmrRecord.setGender(request.getGender());
                bmrRecord.setBmr(request.getBmr());
                bmrRecord.setActivityLevel(request.getActivityLevel());
                bmrRecord.setMaintenanceCalories(request.getMaintenanceCalories());
                bmrRecord.setRecordDate(request.getRecordDate());

                BmrRecord updated = bmrRecordRepository.save(bmrRecord);

                return bmrRecordMapper.toResponse(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public BmrRecordResponse getBmr(Long id) {

                BmrRecord bmrRecord = bmrRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMR record not found with id: " + id));

                return bmrRecordMapper.toResponse(bmrRecord);
        }

        @Override
        @Transactional(readOnly = true)
        public List<BmrRecordResponse> getUserBmrRecords(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bmrRecordRepository.findByUserIdOrderByRecordDateDesc(userId)
                                .stream()
                                .map(bmrRecordMapper::toResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public BmrRecordResponse getLatestBmr(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bmrRecordRepository
                                .findTopByUserIdOrderByRecordDateDesc(userId)
                                .map(bmrRecordMapper::toResponse)
                                .orElse(null);
        }

        @Override
        public void deleteBmr(Long id) {

                BmrRecord bmrRecord = bmrRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "BMR record not found with id: " + id));

                bmrRecordRepository.delete(bmrRecord);
        }
}