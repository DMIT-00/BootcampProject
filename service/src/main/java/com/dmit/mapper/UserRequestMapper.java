package com.dmit.mapper;

import com.dmit.dto.UserRequestDto;
import com.dmit.entity.User;

public class UserRequestMapper {
    public static User fromDto(UserRequestDto userRequestDto) {
        return new User(
                userRequestDto.getId(),
                userRequestDto.getEmail(),
                userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                userRequestDto.getPatronymic(),
                RoleMapper.fromDto(userRequestDto.getRoles())
        );
    }

    public static UserRequestDto toDto(User user) {
        return new UserRequestDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                RoleMapper.toDto(user.getRoles())
        );
    }
}
