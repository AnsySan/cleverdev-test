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

        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        return userMapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto, long id) {
        User user = findById(id);
        user = userMapper.toEntity(userDto);
        user.setId(id);
        return userMapper.toDto(user);
    }

    public UserDto deleteUser(long id) {
        User user = findById(id);
        userRepository.deleteById(id);
        return userMapper.toDto(user);
    }

    public User saveUserFromOldVersion(String login){
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        return userMapper.toEntity(userDto);
    }

    public Optional<User> findAllUsers(long id) {
        return userRepository.findById(id);
    }
}
