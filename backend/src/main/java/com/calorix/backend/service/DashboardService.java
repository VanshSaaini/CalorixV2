package com.calorix.backend.service;

import com.calorix.backend.dto.dashboard.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(String email);

    DashboardResponse getDashboardSummary(String email);

    DashboardResponse getTodayDashboard(String email);

}