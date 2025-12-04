package com.grits.habittracker.model.request;

import lombok.Value;

@Value
public class LoginRequest {

    String email;

    String password;
}
