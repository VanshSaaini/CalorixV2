package com.calorix.backend.dto.water;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterIntakeResponse {

    private Long id;

    private Double litres;

    private LocalDate recordDate;
}