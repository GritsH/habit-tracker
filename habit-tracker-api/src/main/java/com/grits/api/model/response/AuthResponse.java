package com.grits.api.model.response;

import lombok.Value;

@Value
public class AuthResponse {

    UserResponse user;

    String accessToken;

    String refreshToken;

    String tokenType = "Bearer";
}
