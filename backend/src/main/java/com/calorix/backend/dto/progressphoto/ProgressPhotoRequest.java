package com.calorix.backend.dto.progressphoto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressPhotoRequest {

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String description;

    @NotNull(message = "Record date is required")
    private LocalDate recordDate;
}