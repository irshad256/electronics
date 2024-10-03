package com.spring.electronics.user;

import com.spring.electronics.role.RoleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class UserDto {

    private long id;

    private String title;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private String password;

    private List<RoleDto> roles;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private boolean accountLocked;

    private boolean enabled;
}
