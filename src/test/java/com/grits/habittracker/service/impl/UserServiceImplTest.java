package com.grits.habittracker.service.impl;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserAlreadyExistsException;
import com.grits.habittracker.exception.UserNotFoundException;
import com.grits.habittracker.mapper.UserMapper;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import com.grits.habittracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl service;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private UserResponse mockedResponse;
    private User user;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
                "test@example.com",
                "password123",
                "John",
                "Doe",
                "userName!@!!"
        );

        loginRequest = new LoginRequest(
                "test@example.com",
                "password123"
        );

        encodedPassword = "encodedPassword123";

        user = new User();
        user.setId("id");
        user.setEmail("test@example.com");
        user.setPassword(encodedPassword);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("userName!@!!");

        mockedResponse = new UserResponse(
                "test@example.com",
                encodedPassword,
                "John",
                "Doe",
                "userName!@!!"
        );
    }

    @Test
    @DisplayName("should register a new user")
    void signUpUser_Success() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(userMapper.dtoToEntity(signupRequest)).thenReturn(user);

        service.signUpUser(signupRequest);

        verify(repository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userMapper).dtoToEntity(signupRequest);
        verify(repository).save(user);

        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    @DisplayName("should throw exception when registering existing email")
    void signUp_EmailAlreadyExists_ThrowsUserAlreadyExistsException() {
        when(repository.existsByEmail(anyString())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> service.signUpUser(signupRequest));

        assertEquals("User with email already exists", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("should authenticate existing user")
    void loginUser_Success() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(true);
        when(userMapper.entityToDto(user)).thenReturn(mockedResponse);

        UserResponse result = service.loginUser(loginRequest);

        assertNotNull(result);
        assertEquals(result.getEmail(), loginRequest.getEmail());

        verify(repository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", encodedPassword);
        verify(userMapper).entityToDto(user);

    }

    @Test
    @DisplayName("should throw exception when authenticating non-existent user")
    void loginUser_ThrowsUserNotFoundException() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> service.loginUser(loginRequest));

        assertEquals("User with this email doesn't exist", exception.getMessage());

        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("should throw exception when password is invalid")
    void loginUser_InvalidPassword_ThrowsInvalidCredentialsException() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                () -> service.loginUser(loginRequest));

        assertEquals("Invalid credentials", exception.getMessage());

        verify(repository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", encodedPassword);
    }

    @Test
    @DisplayName("should find user by username")
    void getUserByUsername_Success() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userMapper.entityToDto(user)).thenReturn(mockedResponse);

        UserResponse result = service.getUserByUsername("userName!@!!");

        assertNotNull(result);
        assertEquals(result.getUsername(), "userName!@!!");

        verify(repository).findByUsername("userName!@!!");
        verify(userMapper).entityToDto(user);
    }

    @Test
    @DisplayName("should not find user by invalid username")
    void getUserByUsername_InvalidUsername_ThrowsUserNotFoundException() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> service.getUserByUsername("bad_username"));

        assertEquals("User not found", exception.getMessage());

        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
}