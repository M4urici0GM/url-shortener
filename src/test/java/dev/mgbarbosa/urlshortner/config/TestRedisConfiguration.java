package dev.mgbarbosa.urlshortner.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;


@TestConfiguration
public class TestRedisConfiguration {
    private final RedisServer redisServer;

    public TestRedisConfiguration(RedisServer redisServer) {
        this.redisServer = redisServer;
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
