package dev.mgbarbosa.urlshortner.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class JwtToken {
    private String token;
    private UUID tokenIdentifier;
    private Instant createdAt;
    private Instant expiresAt;

    public JwtToken(String token, Instant createdAt, Instant expiresAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public JwtToken(String token, Instant createdAt, Instant expiresAt, UUID tokenIdentifier) {
        this(token, createdAt, expiresAt);
        this.tokenIdentifier = tokenIdentifier;
    }
}
