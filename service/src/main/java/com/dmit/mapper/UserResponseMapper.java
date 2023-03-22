package com.dmit.mapper;

import com.dmit.dto.UserResponseDto;
import com.dmit.entity.User;

public class UserResponseMapper {
    public static User fromDto(UserResponseDto userResponseDto) {
        return new User(
                userResponseDto.getId(),
                userResponseDto.getEmail(),
                userResponseDto.getFirstName(),
                userResponseDto.getLastName(),
                userResponseDto.getPatronymic(),
                RoleMapper.fromDto(userResponseDto.getRoles())
        );
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                RoleMapper.toDto(user.getRoles())
        );
    }
}
