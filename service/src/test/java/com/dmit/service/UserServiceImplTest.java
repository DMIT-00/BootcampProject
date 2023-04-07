package com.dmit.service;

import com.dmit.dao.RoleDao;
import com.dmit.dao.UserDao;
import com.dmit.dto.RoleDto;
import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import com.dmit.entity.User;
import com.dmit.exception.AlreadyExistsException;
import com.dmit.exception.NotFoundException;
import com.dmit.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testAddUserThrowsAlreadyExistsException() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto(
                UUID.randomUUID(),
                "test@email.com",
                "Alex",
                "John",
                "Ben",
                new HashSet<>()
        );

        when(userDao.existsByEmail(userRequestDto.getEmail())).thenReturn(true);

        // When
        // Then
        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> userService.addUser(userRequestDto)
        );
        assertEquals("Email error: already taken! Email: " + userRequestDto.getEmail(), exception.getMessage());
    }

    @Test
    void testAddUserThrowsAlreadyExistsExceptionWhenIdAlreadyExists() {
        // Given
        UUID userId = UUID.randomUUID();
        UserRequestDto userRequestDto = new UserRequestDto(
                userId,
                "test@email.com",
                "Alex",
                "John",
                "Ben",
                new HashSet<>()
        );

        when(userDao.existsById(userId)).thenReturn(true);

        // When
        // Then
        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> userService.addUser(userRequestDto)
        );
        assertEquals("Id error: already taken! Id: " + userId, exception.getMessage());
    }

    @Test
    void testAddUserThrowsNotFoundExceptionForNonexistentRole() {
        // Given
        RoleDto roleDto = new RoleDto("NONEXISTENT_ROLE");
        Set<RoleDto> roles = Collections.singleton(roleDto);
        UserRequestDto userRequestDto = new UserRequestDto(
                UUID.randomUUID(),
                "test@email.com",
                "Alex",
                "John",
                "Ben",
                roles
        );

        when(roleDao.findByRoleName(roleDto.getRoleName())).thenReturn(Optional.empty());

        // When
        // Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.addUser(userRequestDto)
        );
        assertEquals("Role error: role not found! Role: " + roleDto.getRoleName(), exception.getMessage());
    }

    @Test
    void testAddUserThrowsNotFoundExceptionForMultipleRoles() {
        // Given
        RoleDto role1Dto = new RoleDto("ROLE1");
        RoleDto role2Dto = new RoleDto("ROLE2");
        RoleDto role3Dto = new RoleDto("ROLE3");
        Set<RoleDto> roles = new HashSet<>(Arrays.asList(role1Dto, role2Dto, role3Dto));
        UserRequestDto userRequestDto = new UserRequestDto(
                UUID.randomUUID(),
                "test@email.com",
                "Alex",
                "John",
                "Ben",
                roles
        );

        when(roleDao.findByRoleName(role1Dto.getRoleName())).thenReturn(Optional.of(RoleMapper.fromDto(role1Dto)));
        when(roleDao.findByRoleName(role2Dto.getRoleName())).thenReturn(Optional.of(RoleMapper.fromDto(role2Dto)));
        when(roleDao.findByRoleName(role3Dto.getRoleName())).thenReturn(Optional.empty());

        // When
        // Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.addUser(userRequestDto)
        );
        assertEquals("Role error: role not found! Role: " + role3Dto.getRoleName(), exception.getMessage());
    }

    @Test
    void testAddUserCallsUserDaoSave() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto(
                UUID.randomUUID(),
                "test@email.com",
                "Alex",
                "John",
                "Ben",
                new HashSet<>()
        );

        when(userDao.existsByEmail(userRequestDto.getEmail())).thenReturn(false);
        when(userDao.save(any())).thenReturn(new User());

        // When
        userService.addUser(userRequestDto);

        // Then
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    void testFindAllUsersPageableSortedByShouldCallDao() {
        // Given
        List<User> userList = Arrays.asList(
                new User(UUID.randomUUID(), "test1@email.com", "Alex", "Smith", "John", new HashSet<>()),
                new User(UUID.randomUUID(), "test2@email.com", "Ben", "Doe", "Chris", new HashSet<>())
        );
        when(userDao.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(userList));

        // When
        List<UserResponseDto> userResponseDtoList = userService.findAllUsersPageableSortedBy(0, 10, "email");

        // Then
        verify(userDao, times(1)).findAll(PageRequest.of(0, 10, Sort.by("email")));
        assertEquals(2, userResponseDtoList.size());
        assertEquals("Alex", userResponseDtoList.get(0).getFirstName());
        assertEquals("Doe", userResponseDtoList.get(1).getLastName());
    }

    @Test
    void testFindUserByIdShouldCallDao() {
        // Given
        User user = new User(
                UUID.randomUUID(),
                "test1@email.com",
                "Alex",
                "Smith",
                "John",
                new HashSet<>()
        );

        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        UserResponseDto userResponseDto = userService.findUserById(user.getId());

        // Then
        verify(userDao, times(1)).findById(user.getId());
        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getFirstName(), userResponseDto.getFirstName());
        assertEquals(user.getLastName(), userResponseDto.getLastName());
        assertEquals(user.getPatronymic(), userResponseDto.getPatronymic());
    }

    @Test
    void testFindUserByIdShouldThrow() {
        // Given
        User user = new User(
                UUID.randomUUID(),
                "test1@email.com",
                "Alex",
                "Smith",
                "John",
                new HashSet<>()
        );

        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        // When
        // Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.findUserById(user.getId())
        );
        assertEquals("User not found! ID: " + user.getId(), exception.getMessage());
    }

}