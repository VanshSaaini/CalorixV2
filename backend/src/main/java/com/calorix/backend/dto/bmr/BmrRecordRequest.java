package com.calorix.backend.dto.bmr;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BmrRecordRequest {

    private Integer age;

    private Double weight;

    private Double height;

    private String gender;

    private Double bmr;

    private String activityLevel;

    private Double maintenanceCalories;

    private LocalDate recordDate;
}