package com.dmit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<RoleDto> roles = new HashSet<>();
}
