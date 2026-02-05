package com.grits.server.ratelimiting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean isAllowed(String key, int maxRequests, int seconds) {
        String redisKey = "ratelimit:" + key;
        long count = redisTemplate.opsForValue().increment(redisKey);
        if (count == 1) {
            redisTemplate.expire(redisKey, Duration.ofSeconds(seconds));
        }

        return count <= maxRequests;
    }
}
