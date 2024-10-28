package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.dto.UserDto;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/get-login")
    public Optional<User> getLogin(@RequestParam long userId) {
        return userService.findAllUsers(userId);
    }

    @PutMapping("/update")
    public UserDto update(@RequestBody @Valid UserDto userDto, long userId) {
        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/user/delete")
    public UserDto delete(@RequestParam long userId) {
        return userService.deleteUser(userId);
    }
}
