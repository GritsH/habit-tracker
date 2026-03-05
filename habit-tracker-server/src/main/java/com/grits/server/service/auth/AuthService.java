package com.grits.server.service.auth;

import com.grits.api.model.response.AuthResponse;
import com.grits.server.jwt.JwtTokenProvider;
import com.grits.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String generateAccessToken(String userId) {
        return jwtTokenProvider.generateAccessToken(userId);
    }

    public String generateRefreshToken(String userId) {
        return jwtTokenProvider.generateRefreshToken(userId);
    }

    public String extractAndValidateToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return token;
        }
        return null;
    }

    public void logout(HttpServletRequest request, String refreshToken) {
        String accessToken = extractToken(request);
        if (accessToken != null && refreshToken != null) {
            jwtTokenProvider.invalidateTokens(accessToken, refreshToken);
        }
        SecurityContextHolder.clearContext();
    }

    public Authentication createAuthentication(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

    public AuthResponse refresh(String refreshToken, UserService userService) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        jwtTokenProvider.deleteRefreshToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);
        return new AuthResponse(
                userService.getUserById(userId),
                newAccessToken,
                newRefreshToken
        );
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
