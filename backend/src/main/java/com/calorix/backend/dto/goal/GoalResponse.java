package com.calorix.backend.dto.goal;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalResponse {

    private Long id;

    private String goalType;

    private Double targetWeight;

    private Double targetCalories;

    private Double weeklyTarget;

    private LocalDate startDate;

    private LocalDate targetDate;

    private Boolean completed;
}