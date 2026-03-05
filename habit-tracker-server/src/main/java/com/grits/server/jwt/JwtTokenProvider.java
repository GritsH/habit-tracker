package com.grits.server.jwt;

import com.grits.server.exception.InvalidCredentialsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private SecretKey secretKey;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.blacklist.prefix}")
    private String blacklistPrefix;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, accessTokenExpiration);
    }

    public String generateRefreshToken(String userId) {
        String token = generateToken(userId, refreshTokenExpiration);
        String key = "refresh:" + getJtiFromToken(token);
        redisTemplate.opsForValue().set(key, userId, Duration.ofMillis(refreshTokenExpiration));
        return token;
    }

    public boolean validateToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                throw new InvalidCredentialsException("Token is blacklisted");
            }
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidCredentialsException("Expired or invalid JWT token");
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            parseToken(token);
            String jti = getJtiFromToken(token);
            return redisTemplate.hasKey("refresh:" + jti);
        } catch (JwtException e) {
            return false;
        }
    }

    public void invalidateTokens(String accessToken, String refreshToken) {
        deleteRefreshToken(refreshToken);
        String accessJti = getJtiFromToken(accessToken);
        Date expiryDate = getExpirationFromToken(accessToken);
        long ttl = expiryDate.getTime() - System.currentTimeMillis();
        if (ttl > 0) {
            redisTemplate.opsForValue().set(blacklistPrefix + accessJti, "blacklisted", Duration.ofMillis(ttl));
        }
    }

    public void deleteRefreshToken(String refreshToken) {
        String refreshJti = getJtiFromToken(refreshToken);
        redisTemplate.delete("refresh:" + refreshJti);
    }

    public String getUserIdFromToken(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    public String getJtiFromToken(String token) {
        return parseToken(token).getPayload().getId();
    }

    public Date getExpirationFromToken(String token) {
        return parseToken(token).getPayload().getExpiration();
    }

    private String generateToken(String id, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                   .subject(id)
                   .id(jti)
                   .issuedAt(now)
                   .expiration(expiryDate)
                   .signWith(secretKey, Jwts.SIG.HS512)
                   .compact();
    }

    private boolean isTokenBlacklisted(String token) {
        String jti = getJtiFromToken(token);
        return redisTemplate.hasKey(blacklistPrefix + jti);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}