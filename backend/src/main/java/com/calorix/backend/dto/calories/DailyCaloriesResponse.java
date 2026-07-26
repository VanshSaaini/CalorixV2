package com.calorix.backend.dto.calories;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyCaloriesResponse {

    private Long id;

    private Double consumedCalories;

    private Double burnedCalories;

    private Double remainingCalories;

    private LocalDate recordDate;
}