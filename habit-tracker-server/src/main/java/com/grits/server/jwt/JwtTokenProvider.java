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

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey secretKey;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.blacklist.prefix}")
    private String blacklistPrefix;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .subject(id)
                .id(jti)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getPayload().getSubject();
    }

    public String getJtiFromToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getPayload().getId();
    }

    public Date getExpirationFromToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getPayload().getExpiration();
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

    public void invalidateToken(String token) {
        try {
            String jti = getJtiFromToken(token);
            Date expiryDate = getExpirationFromToken(token);
            long ttl = expiryDate.getTime() - System.currentTimeMillis();
            if (ttl > 0) {
                String key = blacklistPrefix + jti;
                redisTemplate.opsForValue().set(key, "blacklisted", Duration.ofMillis(ttl));
            }
        } catch (JwtException e) {
            System.err.println("Error invalidating token: " + e.getMessage());
        }
    }

    private boolean isTokenBlacklisted(String token) {
        String jti = getJtiFromToken(token);
        String key = blacklistPrefix + jti;
        return redisTemplate.hasKey(key);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}