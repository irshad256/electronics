package com.spring.electronics.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateProfileRequest {

    private String title;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private String oldPassword;

    private String newPassword;
}
