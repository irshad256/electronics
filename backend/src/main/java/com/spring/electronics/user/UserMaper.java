package com.spring.electronics.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMaper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto userToUserDto(User user);

    List<UserDto> userListToUserDtoList(List<User> list);
}
