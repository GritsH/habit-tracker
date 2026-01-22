package com.grits.api.model.response;

import lombok.Value;

@Value
public class UserResponse {

    String id;

    String email;

    String firstName;

    String lastName;

    String username;
}
