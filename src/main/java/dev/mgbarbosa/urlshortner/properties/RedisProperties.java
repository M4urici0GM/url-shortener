package dev.mgbarbosa.urlshortner.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisProperties {
    @Getter
    private int redisPort;

    @Getter
    private String redisHost;

    public RedisProperties(
            @Value("${spring.redis.port}") int redisPort,
            @Value("${spring.redis.host}") String redisHost) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }
}
