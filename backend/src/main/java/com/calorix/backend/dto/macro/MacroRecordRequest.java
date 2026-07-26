package com.calorix.backend.dto.macro;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacroRecordRequest {

    private Double calories;

    private Double protein;

    private Double carbohydrates;

    private Double fats;

    private String goal;

    private LocalDate recordDate;
}