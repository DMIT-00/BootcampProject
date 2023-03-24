package com.dmit.service;

import com.dmit.dao.RoleDao;
import com.dmit.dao.UserDao;
import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import com.dmit.entity.Role;
import com.dmit.entity.User;
import com.dmit.exception.AlreadyExistsException;
import com.dmit.exception.InvalidFieldException;
import com.dmit.exception.NotFoundException;
import com.dmit.mapper.UserRequestMapper;
import com.dmit.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;


    @Override
    @Transactional
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        // TODO: email format check
        if (userRequestDto.getEmail().length() < 3 || userRequestDto.getEmail().length() > 50)
            throw new InvalidFieldException("Email should be at least 3 characters and maximum 20 characters!");

        if (userRequestDto.getFirstName().length() < 1 || userRequestDto.getFirstName().length() > 20)
            throw new InvalidFieldException("First name should be at least 1 character and maximum 20 characters!");

        if (userRequestDto.getLastName().length() < 1 || userRequestDto.getLastName().length() > 40)
            throw new InvalidFieldException("Last name should be at least 1 character and maximum 40 characters!");

        if (userRequestDto.getPatronymic().length() < 1 || userRequestDto.getPatronymic().length() > 40)
            throw new InvalidFieldException("Patronymic should be at least 1 character and maximum 40 characters!");

        if (userDao.existsByEmail(userRequestDto.getEmail()))
            throw new AlreadyExistsException("User with this email already exists! Email: " + userRequestDto.getEmail());

        if (userRequestDto.getId() != null && userDao.existsById(userRequestDto.getId()))
            throw new AlreadyExistsException("User with this id already exists! Id: " + userRequestDto.getId());

        // TODO: more checks

        User user = UserRequestMapper.fromDto(userRequestDto);

        // Check if roles exist
        Set<Role> roles = user.getRoles()
                .stream()
                .map(role -> roleDao.findByRoleName(role.getRoleName()).orElseThrow(
                                () -> new NotFoundException("Role not found! Role: " + role.getRoleName())
                ))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        return UserResponseMapper.toDto(userDao.save(user));
    }

    @Override
    public List<UserResponseDto> findAllUsersPageableSortedBy(int page, int size, String sort) {
        return userDao.findAll(PageRequest.of(page, size, Sort.by(sort)))
                .stream()
                .map(UserResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
