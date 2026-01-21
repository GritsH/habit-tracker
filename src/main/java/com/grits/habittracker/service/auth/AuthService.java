package com.grits.habittracker.service.auth;

import com.grits.habittracker.jwt.JwtTokenProvider;
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

    public String generateToken(String userId) {
        return jwtTokenProvider.generateToken(userId);
    }

    public String extractAndValidateToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return token;
        }
        return null;
    }

    public void invalidateToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            jwtTokenProvider.invalidateToken(token);
        }
        SecurityContextHolder.clearContext();
    }

    public Authentication createAuthentication(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
