package com.grits.habittracker.service;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.InvalidCredentialsException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private UserResponse userResponse;
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

        userResponse = new UserResponse(
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
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(userMapper.dtoToEntity(signupRequest)).thenReturn(user);

        service.signUpUser(signupRequest);

        verify(repository).save(user);

        after();
    }

    @Test
    @DisplayName("should throw exception when registering existing email")
    void signUp_EmailAlreadyExists_ThrowsUserAlreadyExistsException() {
        when(userMapper.dtoToEntity(signupRequest)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(repository.save(user)).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> service.signUpUser(signupRequest)).isInstanceOf(DataIntegrityViolationException.class);

        after();
    }

    @Test
    @DisplayName("should authenticate existing user")
    void loginUser_Success() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(true);
        when(userMapper.entityToDto(user)).thenReturn(userResponse);

        UserResponse result = service.loginUser(loginRequest);

        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);

        after();
    }

    @Test
    @DisplayName("should throw exception when authenticating non-existent user")
    void loginUser_ThrowsUserNotFoundException() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loginUser(loginRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        after();
    }

    @Test
    @DisplayName("should throw exception when password is invalid")
    void loginUser_InvalidPassword_ThrowsInvalidCredentialsException() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> service.loginUser(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid credentials");

        after();
    }

    @Test
    @DisplayName("should find user by username")
    void getUserByUsername_Success() {
        when(repository.findByUsername("userName!@!!")).thenReturn(Optional.of(user));
        when(userMapper.entityToDto(user)).thenReturn(userResponse);

        UserResponse result = service.getUserByUsername("userName!@!!");

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);

        after();
    }

    @Test
    @DisplayName("should not find user by invalid username")
    void getUserByUsername_InvalidUsername_ThrowsUserNotFoundException() {
        when(repository.findByUsername("bad_username")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUserByUsername("bad_username"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        after();
    }

    private void after() {
        verifyNoMoreInteractions(repository, passwordEncoder, userMapper);
    }
}