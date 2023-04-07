package com.dmit.service;

import com.dmit.dao.RoleDao;
import com.dmit.dao.UserDao;
import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import com.dmit.entity.Role;
import com.dmit.entity.User;
import com.dmit.exception.AlreadyExistsException;
import com.dmit.exception.NotFoundException;
import com.dmit.mapper.UserRequestMapper;
import com.dmit.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;


    @Override
    @Transactional
    public UserResponseDto addUser(@Valid UserRequestDto userRequestDto) {
        if (userDao.existsByEmail(userRequestDto.getEmail()))
            throw new AlreadyExistsException("Email error: already taken! Email: " + userRequestDto.getEmail());

        if (userRequestDto.getId() != null && userDao.existsById(userRequestDto.getId()))
            throw new AlreadyExistsException("Id error: already taken! Id: " + userRequestDto.getId());

        User user = UserRequestMapper.fromDto(userRequestDto);

        // Check if roles exist
        Set<Role> roles = user.getRoles()
                .stream()
                .map(role -> roleDao.findByRoleName(role.getRoleName()).orElseThrow(
                                () -> new NotFoundException("Role error: role not found! Role: " + role.getRoleName())
                ))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        UserResponseDto createdUser = UserResponseMapper.toDto(userDao.save(user));

        log.info("New user created. Id: " + createdUser.getId());

        return createdUser;
    }

    @Override
    public List<UserResponseDto> findAllUsersPageableSortedBy(int page, int size, String sort) {
        return userDao.findAll(PageRequest.of(page, size, Sort.by(sort)))
                .stream()
                .map(UserResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findUserById(UUID id) {
        return UserResponseMapper.toDto(
                userDao.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found! ID: " + id))
        );
    }
}
