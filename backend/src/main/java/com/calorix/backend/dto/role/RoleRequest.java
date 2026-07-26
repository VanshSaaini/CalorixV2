package com.calorix.backend.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

    @NotBlank(message = "Role name is required.")
    @Size(max = 50, message = "Role name cannot exceed 50 characters.")
    @Pattern(
            regexp = "^ROLE_[A-Z_]+$",
            message = "Role name must be in the format ROLE_NAME (e.g. ROLE_USER)."
    )
    private String name;
}