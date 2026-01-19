package com.grits.habittracker.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey secretKey;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.blacklist.prefix}")
    private String blacklistPrefix;

    public JwtTokenProvider(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(id)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                return false;
            }
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public void invalidateToken(String token) {
        try {
            Jws<Claims> claims = parseToken(token);
            Date expiryDate = claims.getPayload().getExpiration();
            long ttl = expiryDate.getTime() - System.currentTimeMillis();
            if (ttl > 0) {
                String key = blacklistPrefix + token;
                redisTemplate.opsForValue().set(key, "blacklisted", Duration.ofMillis(ttl));
            }
        } catch (JwtException e) {
            System.err.println("Error invalidating token: " + e.getMessage());
        }
    }

    private boolean isTokenBlacklisted(String token) {
        String key = blacklistPrefix + token;
        return redisTemplate.hasKey(key);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}