package com.grits.habittracker.controller;

import com.grits.habittracker.jwt.JwtTokenProvider;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import com.grits.habittracker.model.response.AuthResponse;
import com.grits.habittracker.model.response.UserResponse;
import com.grits.habittracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Tag(name = "User API")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public UserController(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user"
    )
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = userService.signUpUser(signupRequest);

        String token = jwtTokenProvider.generateToken(userResponse.getUsername());

        AuthResponse authResponse = new AuthResponse(userResponse, token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Log in user"
    )
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = userService.loginUser(loginRequest);

        String token = jwtTokenProvider.generateToken(userResponse.getUsername());

        AuthResponse authResponse = new AuthResponse(userResponse, token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Log out",
            description = "Terminate user's session"
    )
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/users/{id}")
    @Operation(
            summary = "Get a user by id",
            description = "Returns a single user if found"
    )
    @PreAuthorize("#username == authentication.principal")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
