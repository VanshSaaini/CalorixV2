package com.calorix.backend.dto.walking;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutePointDto {

    private Double latitude;

    private Double longitude;

    private LocalDateTime recordedAt;

    private Integer sequence;
}
