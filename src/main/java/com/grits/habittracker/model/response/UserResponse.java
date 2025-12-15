package com.grits.habittracker.model.response;

import lombok.Value;

@Value
public class UserResponse {

    String email;

    String firstName;

    String lastName;

    String username;
}
