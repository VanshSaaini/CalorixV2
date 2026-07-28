package com.calorix.backend.dto.walking;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalkingSessionResponse {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationSeconds;

    private Double distanceMeters;

    // meters per second, derived — 0 when duration is 0
    private Double avgSpeedMps;

    @Builder.Default
    private List<RoutePointDto> routePoints = new ArrayList<>();
}
