package com.dmit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private UUID id;

    @NotEmpty(message = "Email error: email can't be empty!")
    @Size(min = 5, max = 20, message = "Email error: email length should be 5-20!")
    @Pattern(regexp = "^.+\\@.+\\..+$", message = "Email error: email format a@b.c!")
    private String email;
    @NotEmpty(message = "First name error: First name can't be empty!")
    @Size(min = 1, max = 20, message = "First name error: First name length should be 1-20!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name error: First name should only contain latin letters!")
    private String firstName;
    @NotEmpty(message = "Last name error: Last name can't be empty!")
    @Size(min = 1, max = 40, message = "Last name error: Last name length should be 1-40!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name error: Last name should only contain latin letters!")
    private String lastName;
    @NotEmpty(message = "Patronymic error: Patronymic can't be empty!")
    @Size(min = 1, max = 40, message = "Patronymic error: Patronymic length should be 1-40!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Patronymic error: Patronymic should only contain latin letters!")
    private String patronymic;
    private Set<RoleDto> roles = new HashSet<>();
}
