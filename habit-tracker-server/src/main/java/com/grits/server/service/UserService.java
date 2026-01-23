package com.grits.server.service;

import com.grits.api.model.request.LoginRequest;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.UserResponse;
import com.grits.server.dao.UserDao;
import com.grits.server.entity.User;
import com.grits.server.exception.InvalidCredentialsException;
import com.grits.server.mapper.UserMapper;
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

    public UserResponse getUserById(String id) {
        log.debug("Get user with ID: {}", id);

        User user = userDao.getUserById(id);

        return userMapper.entityToDto(user);
    }
}
