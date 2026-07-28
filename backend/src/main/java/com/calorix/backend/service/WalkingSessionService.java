package com.calorix.backend.service;

import com.calorix.backend.dto.walking.WalkingSessionRequest;
import com.calorix.backend.dto.walking.WalkingSessionResponse;
import com.calorix.backend.dto.walking.WalkingSessionSummaryResponse;

import java.util.List;

public interface WalkingSessionService {

    WalkingSessionResponse saveSession(Long userId, WalkingSessionRequest request);

    WalkingSessionResponse getSession(Long id);

    List<WalkingSessionSummaryResponse> getUserSessions(Long userId);

    WalkingSessionSummaryResponse getLatestSession(Long userId);

    void deleteSession(Long id);
}
