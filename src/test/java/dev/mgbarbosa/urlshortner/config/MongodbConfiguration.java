package dev.mgbarbosa.urlshortner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
public class MongodbConfiguration {
    @Bean
    public MongoClientFactoryBean mongoClientFactoryBean(@Value("${spring.data.mongodb.uri}") String uri) {
        System.out.println(uri);
        return null;
    }
}
