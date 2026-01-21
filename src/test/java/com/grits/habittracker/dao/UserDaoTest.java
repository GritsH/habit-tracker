package com.grits.habittracker.dao;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.UserAlreadyExistsException;
import com.grits.habittracker.exception.UserNotFoundException;
import com.grits.habittracker.model.response.UserResponse;
import com.grits.habittracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDao userDao;

    private UserResponse userResponse;
    private User user;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        userResponse = mock(UserResponse.class);
    }

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("should save a new user")
    void save() {
        userDao.saveUser(user);

        verify(repository).save(user);
    }

    @Test
    @DisplayName("should throw exception when saving with existing email")
    void saveWithException() {
        when(repository.save(user)).thenThrow(UserAlreadyExistsException.class);

        assertThatThrownBy(() -> userDao.saveUser(user)).isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    @DisplayName("should find user by email")
    void getUserByEmail() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userDao.getUserByEmail("test@example.com");

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "habits", "password")
                .isEqualTo(userResponse);
    }

    @Test
    @DisplayName("should throw exception when getting non-existent user")
    void getByEmailWithUserException() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDao.getUserByEmail("test@example.com"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User test@example.com not found");
    }

    @Test
    @DisplayName("should find user by id")
    void getUserById() {
        when(repository.findById("id")).thenReturn(Optional.of(user));

        User result = userDao.getUserById("id");

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "habits", "password")
                .isEqualTo(userResponse);
    }

    @Test
    @DisplayName("should not find user by invalid id and throw exception")
    void getUserByIdWithException() {
        when(repository.findById("bad_id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDao.getUserById("bad_id"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User bad_id not found");
    }
}