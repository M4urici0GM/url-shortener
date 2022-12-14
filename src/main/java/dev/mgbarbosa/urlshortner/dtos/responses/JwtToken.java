package dev.mgbarbosa.urlshortner.dtos.responses;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class JwtToken {
    private final String token;
    private final UUID tokenIdentifier;
    private final Instant createdAt;
    private final Instant expiresAt;
}
