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
import com.grits.habittracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void signUpUser(SignupRequest signupRequest) {
        log.info("Signing up new user with email: {}", signupRequest.getEmail());

        if (repository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with email already exists");
        }

        User user = userMapper.dtoToEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        repository.save(user);

        log.info("User signed up successfully");
    }

    @Override
    public UserResponse loginUser(LoginRequest loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());

        Optional<User> user = repository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with this email doesn't exist");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        log.info("User with email {} logged in successfully", user.get().getEmail());
        return userMapper.entityToDto(user.get());
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        log.debug("Get user with username: {}", username);

        Optional<User> user = repository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return userMapper.entityToDto(user.get());
    }
}
