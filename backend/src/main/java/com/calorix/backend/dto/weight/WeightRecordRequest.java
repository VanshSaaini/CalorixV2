package com.calorix.backend.dto.weight;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightRecordRequest {

    private Double weight;

    private LocalDate recordDate;
}