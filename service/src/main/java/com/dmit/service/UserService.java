package com.dmit.service;

import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);
    List<UserResponseDto> findAllUsersPageable(int page, int size);
}
