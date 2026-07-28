package com.calorix.backend.service.impl;

import com.calorix.backend.dto.walking.WalkingSessionRequest;
import com.calorix.backend.dto.walking.WalkingSessionResponse;
import com.calorix.backend.dto.walking.WalkingSessionSummaryResponse;
import com.calorix.backend.entity.User;
import com.calorix.backend.entity.WalkingSession;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.WalkingSessionMapper;
import com.calorix.backend.repository.UserRepository;
import com.calorix.backend.repository.WalkingSessionRepository;
import com.calorix.backend.service.WalkingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WalkingSessionServiceImpl implements WalkingSessionService {

    private final WalkingSessionRepository walkingSessionRepository;
    private final UserRepository userRepository;
    private final WalkingSessionMapper walkingSessionMapper;

    @Override
    public WalkingSessionResponse saveSession(Long userId, WalkingSessionRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        WalkingSession session = walkingSessionMapper.toEntity(request);
        session.setUser(user);

        return walkingSessionMapper.toResponse(
                walkingSessionRepository.save(session));
    }

    @Override
    @Transactional(readOnly = true)
    public WalkingSessionResponse getSession(Long id) {

        WalkingSession session = walkingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Walking session not found with id: " + id));

        return walkingSessionMapper.toResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalkingSessionSummaryResponse> getUserSessions(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return walkingSessionRepository.findByUserIdOrderByStartTimeDesc(userId)
                .stream()
                .map(walkingSessionMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WalkingSessionSummaryResponse getLatestSession(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        WalkingSession session = walkingSessionRepository.findFirstByUserIdOrderByStartTimeDesc(userId);

        if (session == null) {
            throw new ResourceNotFoundException("No walking sessions found for user id: " + userId);
        }

        return walkingSessionMapper.toSummaryResponse(session);
    }

    @Override
    public void deleteSession(Long id) {

        if (!walkingSessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Walking session not found with id: " + id);
        }

        walkingSessionRepository.deleteById(id);
    }
}
