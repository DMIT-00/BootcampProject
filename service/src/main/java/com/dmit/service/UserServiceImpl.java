package com.dmit.service;

import com.dmit.dao.UserDao;
import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import com.dmit.entity.User;
import com.dmit.exception.InvalidFieldException;
import com.dmit.mapper.UserRequestMapper;
import com.dmit.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getEmail().length() < 3 || userRequestDto.getEmail().length() > 50)
            throw new InvalidFieldException("Email should be at least 3 characters and maximum 20 characters!");

        if (userRequestDto.getFirstName().length() < 1 || userRequestDto.getFirstName().length() > 20)
            throw new InvalidFieldException("First name should be at least 1 character and maximum 20 characters!");

        if (userRequestDto.getLastName().length() < 1 || userRequestDto.getLastName().length() > 40)
            throw new InvalidFieldException("Last name should be at least 1 character and maximum 40 characters!");

        if (userRequestDto.getPatronymic().length() < 1 || userRequestDto.getPatronymic().length() > 40)
            throw new InvalidFieldException("Patronymic should be at least 1 character and maximum 40 characters!");

        // TODO: more checks

        User user =  UserRequestMapper.fromDto(userRequestDto);

        return UserResponseMapper.toDto(userDao.save(user));
    }

    @Override
    public List<UserResponseDto> findAllUsersPageable(int page, int size) {
        return userDao.findAll(PageRequest.of(page, size)).stream()
                .map(UserResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
