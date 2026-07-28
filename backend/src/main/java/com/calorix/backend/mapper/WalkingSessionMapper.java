package com.calorix.backend.mapper;

import com.calorix.backend.dto.walking.RoutePointDto;
import com.calorix.backend.dto.walking.WalkingSessionRequest;
import com.calorix.backend.dto.walking.WalkingSessionResponse;
import com.calorix.backend.dto.walking.WalkingSessionSummaryResponse;
import com.calorix.backend.entity.WalkingRoutePoint;
import com.calorix.backend.entity.WalkingSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalkingSessionMapper {

    public WalkingSession toEntity(WalkingSessionRequest dto) {
        WalkingSession session = WalkingSession.builder()
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .durationSeconds(dto.getDurationSeconds())
                .distanceMeters(dto.getDistanceMeters())
                .build();

        if (dto.getRoutePoints() != null) {
            int sequence = 0;
            for (RoutePointDto pointDto : dto.getRoutePoints()) {
                WalkingRoutePoint point = WalkingRoutePoint.builder()
                        .latitude(pointDto.getLatitude())
                        .longitude(pointDto.getLongitude())
                        .recordedAt(pointDto.getRecordedAt())
                        .sequence(pointDto.getSequence() != null ? pointDto.getSequence() : sequence)
                        .build();
                session.addRoutePoint(point);
                sequence++;
            }
        }

        return session;
    }

    public WalkingSessionResponse toResponse(WalkingSession entity) {
        return WalkingSessionResponse.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .durationSeconds(entity.getDurationSeconds())
                .distanceMeters(entity.getDistanceMeters())
                .avgSpeedMps(avgSpeed(entity.getDistanceMeters(), entity.getDurationSeconds()))
                .routePoints(toRoutePointDtos(entity.getRoutePoints()))
                .build();
    }

    public WalkingSessionSummaryResponse toSummaryResponse(WalkingSession entity) {
        return WalkingSessionSummaryResponse.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .durationSeconds(entity.getDurationSeconds())
                .distanceMeters(entity.getDistanceMeters())
                .avgSpeedMps(avgSpeed(entity.getDistanceMeters(), entity.getDurationSeconds()))
                .build();
    }

    private List<RoutePointDto> toRoutePointDtos(List<WalkingRoutePoint> points) {
        return points.stream()
                .map(p -> RoutePointDto.builder()
                        .latitude(p.getLatitude())
                        .longitude(p.getLongitude())
                        .recordedAt(p.getRecordedAt())
                        .sequence(p.getSequence())
                        .build())
                .toList();
    }

    private Double avgSpeed(Double distanceMeters, Long durationSeconds) {
        if (distanceMeters == null || durationSeconds == null || durationSeconds == 0) {
            return 0.0;
        }
        return distanceMeters / durationSeconds;
    }
}
