package com.grits.api.service;

import com.grits.api.model.request.LoginRequest;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.UserResponse;

public interface UserService {

    UserResponse signUpUser(SignupRequest signupRequest);

    UserResponse loginUser(LoginRequest loginRequest);

    UserResponse getUserById(String id);
}
