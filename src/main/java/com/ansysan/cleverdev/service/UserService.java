package com.ansysan.cleverdev.service;

import com.ansysan.cleverdev.dto.UserDto;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.exception.NotFoundException;
import com.ansysan.cleverdev.mapper.UserMapper;
import com.ansysan.cleverdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findById(long id) {
        log.debug("Find User by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
    }

    public UserDto createUser(UserDto userDto) {
        log.info("Creating user: {}", userDto);
        User user = userMapper.toEntity(userDto);

        return userMapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto, long id) {
        log.debug("Updating user: {}", userDto);
        User user = findById(id);
        user = userMapper.toEntity(userDto);
        user.setId(id);
        return userMapper.toDto(user);
    }

    public UserDto deleteUser(long id) {
        log.debug("Deleting user: {}", id);
        User user = findById(id);
        userRepository.deleteById(id);
        return userMapper.toDto(user);
    }

    public User saveUserFromOldVersion(String login){
        log.debug("Saving user from old version: {}", login);
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        return userMapper.toEntity(userDto);
    }

    public Optional<User> findAllUsers(long id) {
        log.debug("Finding all users: {}", id);
        return userRepository.findById(id);
    }
}
