package dev.mgbarbosa.urlshortner.strategies.authentication;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import javax.management.InvalidApplicationException;
import org.springframework.stereotype.Component;

@Component("refreshTokenAuthenticationStrategy")
public class RefreshTokenAuthenticationStrategy implements AuthenticationStrategy {
    private final SecurityCachingRepository _cacheRepository;

    public RefreshTokenAuthenticationStrategy(SecurityCachingRepository cacheRepository) {
        _cacheRepository = cacheRepository;
    }

    @Override
    public AuthenticateResponseDto execute(AuthenticateRequestDto request) throws InvalidApplicationException {
        return null;
    }
}
