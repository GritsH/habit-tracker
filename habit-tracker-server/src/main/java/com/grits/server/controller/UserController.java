package com.grits.server.controller;

import com.grits.api.controller.UserOperations;
import com.grits.api.model.request.LoginRequest;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.AuthResponse;
import com.grits.api.model.response.UserResponse;
import com.grits.server.service.UserService;
import com.grits.server.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserOperations {

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = userService.signUpUser(signupRequest);
        String token = authService.generateToken(userResponse.getId());
        return ResponseEntity.ok(new AuthResponse(userResponse, token));
    }

    @Override
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = userService.loginUser(loginRequest);
        String token = authService.generateToken(userResponse.getId());
        return ResponseEntity.ok(new AuthResponse(userResponse, token));
    }

    @Override
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.invalidateToken(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserResponse> getUserByUserId(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
