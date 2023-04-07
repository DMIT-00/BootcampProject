package com.dmit.service;

import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * The UserService interface provides methods for managing user data.
 */
@Validated
public interface UserService {
    /**
     * Adds a new user to the system.
     *
     * @param userRequestDto The UserRequestDto object containing the user's data.
     * @return A UserResponseDto object representing the added user.
     */
    UserResponseDto addUser(@Valid UserRequestDto userRequestDto);

    /**
     * Returns a page of users sorted by the given criteria.
     *
     * @param page The page number (0-indexed) of the results to return.
     * @param size The number of results per page.
     * @param sort The field to sort by.
     * @return A List of UserResponseDto objects representing the users on the requested page.
     */
    List<UserResponseDto> findAllUsersPageableSortedBy(int page, int size, String sort);

    /**
     * Returns the user with specified ID.
     *
     * @param id User ID.
     * @return UserResponseDto object representing the user.
     */
    UserResponseDto findUserById(UUID id);
}
