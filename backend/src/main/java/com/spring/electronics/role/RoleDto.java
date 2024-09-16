package com.spring.electronics.role;

import com.spring.electronics.user.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class RoleDto {

    private long id;

    private String name;

    private List<UserDto> users;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
