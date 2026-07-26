package com.calorix.backend.dto.user;

import com.calorix.backend.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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
public class UserUpdateRequest {

    @Size(max = 50, message = "First name cannot exceed 50 characters.")
    private String firstName;

    @Size(max = 50, message = "Last name cannot exceed 50 characters.")
    private String lastName;

    @Email(message = "Please enter a valid email address.")
    private String email;

    @Min(value = 10, message = "Age must be at least 10.")
    @Max(value = 120, message = "Age cannot exceed 120.")
    private Integer age;

    @Positive(message = "Height must be greater than 0.")
    private Double height;

    private Gender gender;
}