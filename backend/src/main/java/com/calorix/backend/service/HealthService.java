package com.calorix.backend.service;

import com.calorix.backend.dto.health.HealthResponse;

public interface HealthService {

    HealthResponse getHealthStatus();

}