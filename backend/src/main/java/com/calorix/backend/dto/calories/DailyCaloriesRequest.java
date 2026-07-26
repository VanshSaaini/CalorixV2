package com.calorix.backend.dto.calories;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyCaloriesRequest {

    private Double consumedCalories;

    private Double burnedCalories;

    private Double remainingCalories;

    private LocalDate recordDate;
}