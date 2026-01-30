package com.grits.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequest {

    @Email
    @NotEmpty
    String email;

    @NotBlank
    String password;
}
