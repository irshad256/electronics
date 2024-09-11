package com.spring.electronics.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    private String title;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

    private boolean termsAndConditionCheck;
}
