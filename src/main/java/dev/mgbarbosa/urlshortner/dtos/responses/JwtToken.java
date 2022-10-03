package dev.mgbarbosa.urlshortner.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class JwtToken {
    private String token;
    private Instant createdAt;
    private Instant expiresAt;
}
