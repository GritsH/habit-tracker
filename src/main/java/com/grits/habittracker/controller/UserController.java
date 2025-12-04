package com.grits.habittracker.controller;

import com.grits.habittracker.model.response.UserResponse;
import com.grits.habittracker.model.request.LoginRequest;
import com.grits.habittracker.model.request.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user"
    )
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Log in user"
    )
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        return null; //ResponseEntity.ok(UserResponse.fromEntity());
    }

    @GetMapping("/users/{username}")
    @Operation(
            summary = "Get a user by username",
            description = "Returns a single user if found"
    )
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return null; //ResponseEntity.ok(UserResponse.fromEntity());
    }

}
