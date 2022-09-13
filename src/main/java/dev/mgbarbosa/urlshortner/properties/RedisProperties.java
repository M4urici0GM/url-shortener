package dev.mgbarbosa.urlshortner.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RedisProperties {
    private int redisPort;

    private String redisHost;

    public RedisProperties(
            @Value("${spring.redis.port}") int redisPort,
            @Value("${spring.redis.host}") String redisHost) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }
}
