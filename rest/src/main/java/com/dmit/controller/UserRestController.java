package com.dmit.controller;

import com.dmit.dto.UserRequestDto;
import com.dmit.dto.UserResponseDto;
import com.dmit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestController {
    private static final String DEFAULT_PAGE_SIZE = "10";
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE)
                                                          int size) {
        List<UserResponseDto> users = userService.findAllUsersPageableSortedBy(page, size, "email");
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") UUID id) {
        UserResponseDto user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
}
