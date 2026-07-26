package com.calorix.backend.service.impl;

import com.calorix.backend.dto.health.HealthResponse;
import com.calorix.backend.service.HealthService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponse getHealthStatus() {

        return HealthResponse.builder()
                .status("UP")
                .application("Calorix")
                .version("1.0.0")
                .timestamp(LocalDateTime.now())
                .build();
    }
}