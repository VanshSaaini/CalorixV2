package com.calorix.backend.dto.bmi;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BmiRecordRequest {

    private Double weight;

    private Double height;

    private Double bmi;

    private String category;

    private LocalDate recordDate;
}