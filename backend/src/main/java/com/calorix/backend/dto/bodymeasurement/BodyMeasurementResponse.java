package com.calorix.backend.dto.bodymeasurement;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyMeasurementResponse {

    private Long id;

    private Double neck;
    private Double chest;
    private Double waist;
    private Double hips;
    private Double leftArm;
    private Double rightArm;
    private Double leftThigh;
    private Double rightThigh;
    private Double leftCalf;
    private Double rightCalf;

    private LocalDate recordDate;
}