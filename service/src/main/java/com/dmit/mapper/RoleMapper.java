package com.dmit.mapper;

import com.dmit.dto.RoleDto;
import com.dmit.entity.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static Role fromDto(RoleDto roleDto) {
        return new Role(null, roleDto.getRoleName(), new HashSet<>());
    }

    public static RoleDto toDto(Role role) {
        return new RoleDto(role.getRoleName());
    }

    public static Set<Role> fromDto(Set<RoleDto> roleDto) {
        return roleDto
                .stream()
                .map(RoleMapper::fromDto)
                .collect(Collectors.toSet());
    }

    public static Set<RoleDto> toDto(Set<Role> role) {
        return role
                .stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toSet());
    }
}
