package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.UserDto;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Created user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        log.info("Creating user: {}", userDto);
        return userService.createUser(userDto);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User get"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error get the user")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-login")
    public Optional<User> getLogin(@RequestParam long userId) {
        log.info("Getting login for user: {}", userId);
        return userService.findAllUsers(userId);
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the user")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public UserDto update(@RequestBody @Valid UserDto userDto, long userId) {
        log.info("Updating user: {}", userDto);
        return userService.updateUser(userDto, userId);
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error when deleting a user")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/user/delete")
    public UserDto delete(@RequestParam long userId) {
        log.info("Deleting user: {}", userId);
        return userService.deleteUser(userId);
    }
}
