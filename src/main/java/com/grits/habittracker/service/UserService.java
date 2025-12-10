package com.grits.habittracker.service;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserNotFoundException;
import com.grits.habittracker.mapper.UserMapper;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;
import com.grits.habittracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public void signUpUser(SignupRequest signupRequest) {
        log.info("Signing up new user with email: {}", signupRequest.getEmail());

        User user = userMapper.dtoToEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        repository.save(user);

        log.info("User signed up successfully");
    }

    public UserResponse loginUser(LoginRequest loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());

        User user = repository.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("User with email {} logged in successfully", user.getEmail());
        return userMapper.entityToDto(user);
    }

    public UserResponse getUserByUsername(String username) {
        log.debug("Get user with username: {}", username);

        User user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return userMapper.entityToDto(user);
    }
}
