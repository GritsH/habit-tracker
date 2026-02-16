package com.grits.server.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
@Profile("!test")
public class RateLimitConfig {

    @Value("${SPRING_DATA_REDIS_USERNAME}")
    private String redisUsername;

    @Value("${SPRING_DATA_REDIS_PASSWORD}")
    private String redisPassword;

    @Bean
    public ProxyManager<String> proxyManager(LettuceConnectionFactory connectionFactory) {
        String redisUrl = String.format("redis://%s:%s@%s:%s",
                redisUsername,
                redisPassword,
                connectionFactory.getHostName(),
                connectionFactory.getPort()
        );

        RedisClient client = RedisClient.create(redisUrl);
        RedisCodec<String, byte[]> codec = RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE);
        StatefulRedisConnection<String, byte[]> connection = client.connect(codec);
        return LettuceBasedProxyManager.builderFor(connection).build();
    }

    @Bean
    public BucketConfiguration bucketConfiguration() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(100)
                .refillGreedy(100, Duration.ofMinutes(1))
                .build();

        return BucketConfiguration.builder()
                .addLimit(limit)
                .build();
    }
}
