package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.walking.WalkingSessionRequest;
import com.calorix.backend.dto.walking.WalkingSessionResponse;
import com.calorix.backend.dto.walking.WalkingSessionSummaryResponse;
import com.calorix.backend.service.WalkingSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/walking-sessions")
@RequiredArgsConstructor
public class WalkingSessionController {

    private final WalkingSessionService walkingSessionService;

    /**
     * Save Walking Session (submitted once tracking is stopped)
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<WalkingSessionResponse> saveSession(
            @PathVariable Long userId,
            @Valid @RequestBody WalkingSessionRequest request) {

        WalkingSessionResponse response =
                walkingSessionService.saveSession(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get Walking Session By Id (includes route points)
     */
    @GetMapping("/{id}")
    public ResponseEntity<WalkingSessionResponse> getSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                walkingSessionService.getSession(id)
        );
    }

    /**
     * Get All Walking Sessions Of User (summary — no route points)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WalkingSessionSummaryResponse>> getUserSessions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                walkingSessionService.getUserSessions(userId)
        );
    }

    /**
     * Get Latest Walking Session
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<WalkingSessionSummaryResponse> getLatestSession(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                walkingSessionService.getLatestSession(userId)
        );
    }

    /**
     * Delete Walking Session
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSession(
            @PathVariable Long id) {

        walkingSessionService.deleteSession(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Walking session deleted successfully.")
                        .build()
        );
    }
}
