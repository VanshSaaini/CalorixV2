package com.calorix.backend.controller;

import com.calorix.backend.dto.dashboard.DashboardResponse;
import com.calorix.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Get Complete Dashboard
     */
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getDashboard(email)
        );
    }

    /**
     * Get Dashboard Summary
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getDashboardSummary(email)
        );
    }

    /**
     * Get Today's Dashboard
     */
    @GetMapping("/today")
    public ResponseEntity<DashboardResponse> getTodayDashboard(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getTodayDashboard(email)
        );
    }
}