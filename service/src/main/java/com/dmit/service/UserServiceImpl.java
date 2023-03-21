package com.dmit.service;

import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        // TODO: Implement me!
        throw new UnsupportedOperationException("Implement me!");
    }

    @Override
    public List<UserResponseDto> findAllUsersPageable(int page, int size) {
        // TODO: Implement me!
        throw new UnsupportedOperationException("Implement me!");
    }
}
