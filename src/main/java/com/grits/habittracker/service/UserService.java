package com.grits.habittracker.service;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.mapper.UserMapper;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserDao userDao;

    public UserResponse signUpUser(SignupRequest signupRequest) {
        log.info("Signing up new user with email: {}", signupRequest.getEmail());

        User user = userMapper.dtoToEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userDao.saveUser(user);

        log.info("User signed up successfully");

        return userMapper.entityToDto(user);
    }

    public UserResponse loginUser(LoginRequest loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());

        User user = userDao.getUserByEmail(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("User with email {} logged in successfully", user.getEmail());
        return userMapper.entityToDto(user);
    }

    public UserResponse getUserByUsername(String username) {
        log.debug("Get user with username: {}", username);

        User user = userDao.getUserByUsername(username);

        return userMapper.entityToDto(user);
    }
}
