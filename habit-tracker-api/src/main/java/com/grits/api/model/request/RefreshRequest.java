package com.grits.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RefreshRequest {

    @NotBlank
    String refreshToken;
}
