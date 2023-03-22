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

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "size", defaultValue = "10")
                                                          int size) {
        List<UserResponseDto> users = userService.findAllUsersPageable(page, size);
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
}
