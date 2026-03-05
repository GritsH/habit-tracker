package com.grits.server.controller;

import com.grits.api.controller.UserApi;
import com.grits.api.model.request.LoginRequest;
import com.grits.api.model.request.RefreshRequest;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.AuthResponse;
import com.grits.api.model.response.UserResponse;
import com.grits.server.service.UserService;
import com.grits.server.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<AuthResponse> signup(SignupRequest signupRequest) {
        UserResponse userResponse = userService.signUpUser(signupRequest);
        String token = authService.generateAccessToken(userResponse.getId());
        String refreshToken = authService.generateRefreshToken(userResponse.getId());
        return ResponseEntity.ok(new AuthResponse(userResponse, token, refreshToken));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        UserResponse userResponse = userService.loginUser(loginRequest);
        String token = authService.generateAccessToken(userResponse.getId());
        String refreshToken = authService.generateRefreshToken(userResponse.getId());
        return ResponseEntity.ok(new AuthResponse(userResponse, token, refreshToken));
    }

    @Override
    public ResponseEntity<Void> logout(RefreshRequest refreshRequest, HttpServletRequest request) {
        authService.logout(request, refreshRequest.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserResponse> getUserByUserId(String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<AuthResponse> refresh(RefreshRequest request) {
        AuthResponse response = authService.refresh(request.getRefreshToken(), userService);
        return ResponseEntity.ok(response);
    }
}
