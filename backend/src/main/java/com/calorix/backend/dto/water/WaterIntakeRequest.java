package com.calorix.backend.dto.water;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterIntakeRequest {

    private Double litres;

    private LocalDate recordDate;
}