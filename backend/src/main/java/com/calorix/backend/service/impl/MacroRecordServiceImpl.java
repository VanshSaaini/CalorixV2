package com.calorix.backend.service.impl;

import com.calorix.backend.dto.macro.MacroRecordRequest;
import com.calorix.backend.dto.macro.MacroRecordResponse;
import com.calorix.backend.entity.MacroRecord;
import com.calorix.backend.entity.User;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.MacroRecordMapper;
import com.calorix.backend.repository.MacroRecordRepository;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.service.MacroRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MacroRecordServiceImpl implements MacroRecordService {

        private final MacroRecordRepository macroRecordRepository;
        private final UserRepository userRepository;
        private final MacroRecordMapper macroRecordMapper;

        @Override
        public MacroRecordResponse saveMacros(Long userId, MacroRecordRequest request) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with id: " + userId));

                MacroRecord macroRecord = macroRecordMapper.toEntity(request);
                macroRecord.setUser(user);

                MacroRecord saved = macroRecordRepository.save(macroRecord);

                return macroRecordMapper.toResponse(saved);
        }

        @Override
        public MacroRecordResponse updateMacros(Long id, MacroRecordRequest request) {

                MacroRecord macroRecord = macroRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Macro record not found with id: " + id));

                macroRecord.setCalories(request.getCalories());
                macroRecord.setProtein(request.getProtein());
                macroRecord.setCarbohydrates(request.getCarbohydrates());
                macroRecord.setFats(request.getFats());
                macroRecord.setGoal(request.getGoal());
                macroRecord.setRecordDate(request.getRecordDate());

                MacroRecord updated = macroRecordRepository.save(macroRecord);

                return macroRecordMapper.toResponse(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public MacroRecordResponse getMacros(Long id) {

                MacroRecord macroRecord = macroRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Macro record not found with id: " + id));

                return macroRecordMapper.toResponse(macroRecord);
        }

        @Override
        @Transactional(readOnly = true)
        public List<MacroRecordResponse> getUserMacros(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return macroRecordRepository
                                .findByUserIdOrderByRecordDateDesc(userId)
                                .stream()
                                .map(macroRecordMapper::toResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public MacroRecordResponse getLatestMacros(Long userId) {

                if (!userRepository.existsById(userId)) {
                        throw new ResourceNotFoundException(
                                        "User not found with id: " + userId);
                }

                return macroRecordRepository
                                .findTopByUserIdOrderByRecordDateDesc(userId)
                                .map(macroRecordMapper::toResponse)
                                .orElse(null);
        }

        @Override
        public void deleteMacros(Long id) {

                MacroRecord macroRecord = macroRecordRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Macro record not found with id: " + id));

                macroRecordRepository.delete(macroRecord);
        }
}