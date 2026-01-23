package com.grits.api.controller;

import com.grits.api.model.request.LoginRequest;
import com.grits.api.model.request.SignupRequest;
import com.grits.api.model.response.AuthResponse;
import com.grits.api.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1")
@Tag(name = "User API")
public interface UserApi {

    @PostMapping("/signup")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user"
    )
    ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest);

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Log in user"
    )
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/logout")
    @Operation(
            summary = "Log out",
            description = "Terminate user's session"
    )
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> logout(HttpServletRequest request);

    @GetMapping("/users/{id}")
    @Operation(
            summary = "Get a user by id",
            description = "Returns a single user if found"
    )
    @PreAuthorize("#id == authentication.principal")
    ResponseEntity<UserResponse> getUserByUserId(@PathVariable String id);
}
