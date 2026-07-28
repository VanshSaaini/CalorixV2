package com.calorix.backend.dto.walking;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Lightweight session shape for history/list views — omits route points
 * so we don't pull GPS tracks for every row.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalkingSessionSummaryResponse {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationSeconds;

    private Double distanceMeters;

    private Double avgSpeedMps;
}
