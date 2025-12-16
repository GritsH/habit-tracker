package com.grits.habittracker.model.response;

import lombok.Value;

@Value
public class AuthResponse {

    UserResponse user;

    String token;

    String tokenType = "Bearer";
}
