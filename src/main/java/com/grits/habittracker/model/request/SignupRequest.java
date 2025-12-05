package com.grits.habittracker.model.request;

import lombok.Value;

@Value
public class SignupRequest {

    String email;

    String password;

    String firstName;

    String lastName;

    String username;
}
