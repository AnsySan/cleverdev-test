package com.ansysan.cleverdev.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ansysan.cleverdev.dto.UserDto;
import com.ansysan.cleverdev.entity.User;
import com.ansysan.cleverdev.exception.NotFoundException;
import com.ansysan.cleverdev.mapper.UserMapper;
import com.ansysan.cleverdev.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setLogin("testUser");

        userDto = new UserDto();
        userDto.setLogin("testUser");
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("Post with id 1 not found", exception.getMessage());
    }

    @Test
    void createUser_ShouldReturnUserDto_WhenUserIsCreated() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getLogin(), result.getLogin());
        verify(userMapper).toEntity(userDto);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserDto_WhenUserIsUpdated() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.updateUser(userDto, 1L);

        assertNotNull(result);
        assertEquals(userDto.getLogin(), result.getLogin());
        verify(userRepository).findById(1L);
        verify(userMapper).toEntity(userDto);
        verify(userMapper).toDto(user);
    }

    @Test
    void deleteUser_ShouldReturnDeletedUserDto_WhenUserIsDeleted() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.deleteUser(1L);

        assertNotNull(result);
        assertEquals(userDto.getLogin(), result.getLogin());
        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void saveUserFromOldVersion_ShouldReturnUser_WhenCalled() {
        String login = "oldUser";
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());

        User result = userService.saveUserFromOldVersion(login);

        assertNotNull(result);
        verify(userMapper).toEntity(any(UserDto.class));
    }

    @Test
    void findAllUsers_ShouldReturnOptionalUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findAllUsers(1L);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        verify(userRepository).findById(1L);
    }
}
