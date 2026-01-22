package com.grits.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {

    String generateToken(String userId);

    String extractAndValidateToken(HttpServletRequest request);

    void invalidateToken(HttpServletRequest request);

    Authentication createAuthentication(String token);
}
