package com.calorix.backend.controller;

import com.calorix.backend.dto.health.HealthResponse;
import com.calorix.backend.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping
    public ResponseEntity<HealthResponse> health() {

        return ResponseEntity.ok(
                healthService.getHealthStatus()
        );
    }
}