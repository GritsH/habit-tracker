package com.grits.habittracker.service;

import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.UserResponse;

public interface UserService {

    void signUpUser(SignupRequest signupRequest);

    UserResponse loginUser(LoginRequest loginRequest);

    UserResponse getUserByUsername(String username);
}
