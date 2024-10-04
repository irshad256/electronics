package com.spring.electronics.mapper;

import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);

    List<RoleDto> roleListToRoleDtoList(List<Role> list);
}
