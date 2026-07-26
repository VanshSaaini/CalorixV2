package com.calorix.backend.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Integer age;

    private Double height;

    private String gender;
}