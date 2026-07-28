package com.calorix.backend.dto.walking;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalkingSessionRequest {

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Long durationSeconds;

    @NotNull
    private Double distanceMeters;

    @Builder.Default
    private List<RoutePointDto> routePoints = new ArrayList<>();
}
