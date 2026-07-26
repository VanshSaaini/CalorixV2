package com.calorix.backend.service.impl;

import com.calorix.backend.dto.bodymeasurement.BodyMeasurementRequest;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;
import com.calorix.backend.entity.BodyMeasurement;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.BodyMeasurementMapper;
import com.calorix.backend.repository.BodyMeasurementRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.BodyMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BodyMeasurementServiceImpl implements BodyMeasurementService {

        private final BodyMeasurementRepository bodyMeasurementRepository;
        private final UserRepository userRepository;
        private final BodyMeasurementMapper bodyMeasurementMapper;

        @Override
        public BodyMeasurementResponse saveMeasurement(Long userId,
                        BodyMeasurementRequest request) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with id: " + userId));

                BodyMeasurement measurement = bodyMeasurementMapper.toEntity(request);
                measurement.setUser(user);

                BodyMeasurement saved = bodyMeasurementRepository.save(measurement);

                return bodyMeasurementMapper.toResponse(saved);
        }

        @Override
        public BodyMeasurementResponse updateMeasurement(Long id,
                        BodyMeasurementRequest request) {

                BodyMeasurement measurement = bodyMeasurementRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Body measurement not found with id: " + id));

                measurement.setNeck(request.getNeck());
                measurement.setChest(request.getChest());
                measurement.setWaist(request.getWaist());
                measurement.setHips(request.getHips());
                measurement.setLeftArm(request.getLeftArm());
                measurement.setRightArm(request.getRightArm());
                measurement.setLeftThigh(request.getLeftThigh());
                measurement.setRightThigh(request.getRightThigh());
                measurement.setLeftCalf(request.getLeftCalf());
                measurement.setRightCalf(request.getRightCalf());
                measurement.setRecordDate(request.getRecordDate());

                BodyMeasurement updated = bodyMeasurementRepository.save(measurement);

                return bodyMeasurementMapper.toResponse(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public BodyMeasurementResponse getMeasurement(Long id) {

                BodyMeasurement measurement = bodyMeasurementRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Body measurement not found with id: " + id));

                return bodyMeasurementMapper.toResponse(measurement);
        }

        @Override
        @Transactional(readOnly = true)
        public List<BodyMeasurementResponse> getUserMeasurements(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bodyMeasurementRepository
                                .findByUserIdOrderByRecordDateDesc(userId)
                                .stream()
                                .map(bodyMeasurementMapper::toResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public BodyMeasurementResponse getLatestMeasurement(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return bodyMeasurementRepository
                                .findTopByUserIdOrderByRecordDateDesc(userId)
                                .map(bodyMeasurementMapper::toResponse)
                                .orElse(null);
        }

        @Override
        public void deleteMeasurement(Long id) {

                BodyMeasurement measurement = bodyMeasurementRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Body measurement not found with id: " + id));

                bodyMeasurementRepository.delete(measurement);
        }
}