package com.calorix.backend.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private Double height;

    private String gender;

    private Boolean emailVerified;

    private String role;
}