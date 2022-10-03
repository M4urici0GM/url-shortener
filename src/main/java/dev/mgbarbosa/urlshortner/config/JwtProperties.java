package dev.mgbarbosa.urlshortner.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Base Configuration for JWT token creation.
 */
@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration-seconds}")
    private long expirationTime;

}
