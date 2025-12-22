package com.grits.habittracker.service;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserAlreadyExistsException;
import com.grits.habittracker.exception.UserNotFoundException;
import com.grits.habittracker.mapper.UserMapper;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

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

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(userDao, passwordEncoder, userMapper);
    }

    @Test
    @DisplayName("should register a new user")
    void signUp() {
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(userMapper.dtoToEntity(signupRequest)).thenReturn(user);

        service.signUpUser(signupRequest);

        verify(userDao).saveUser(user);
    }

    @Test
    @DisplayName("should throw exception when registering existing email")
    void signUpWithException() {
        when(userMapper.dtoToEntity(signupRequest)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        doThrow(UserAlreadyExistsException.class).when(userDao).saveUser(user);

        assertThatThrownBy(() -> service.signUpUser(signupRequest)).isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    @DisplayName("should authenticate existing user")
    void loginUser() {
        when(userDao.getUserByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(true);
        when(userMapper.entityToDto(user)).thenReturn(userResponse);

        UserResponse result = service.loginUser(loginRequest);

        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);
    }

    @Test
    @DisplayName("should throw exception when authenticating non-existent user")
    void loginUserWithUserException() {
        when(userDao.getUserByEmail("test@example.com")).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> service.loginUser(loginRequest)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("should throw exception when password is invalid")
    void loginUserWithCredentialsException() {
        when(userDao.getUserByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> service.loginUser(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    @DisplayName("should find user by id")
    void getUserById() {
        when(userDao.getUserById("id")).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userResponse);

        UserResponse result = service.getUserById("id");

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);
    }

    @Test
    @DisplayName("should not find user by invalid id and throw exception")
    void getUserByIdWithException() {
        when(userDao.getUserById("bad_id")).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> service.getUserById("bad_id")).isInstanceOf(UserNotFoundException.class);
    }
}