package com.calorix.backend.dto.health;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {

    private String status;

    private String application;

    private String version;

    private LocalDateTime timestamp;

}