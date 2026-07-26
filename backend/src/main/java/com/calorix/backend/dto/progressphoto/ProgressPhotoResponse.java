package com.calorix.backend.dto.progressphoto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressPhotoResponse {

    private Long id;

    private Long userId;

    private String imageUrl;

    private String description;

    private LocalDate recordDate;
}