package dev.mgbarbosa.urlshortner.strategies.authentication;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.management.InvalidApplicationException;

@Component
public class PasswordAuthenticationStrategy implements AuthenticationStrategy {
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final SecurityCachingRepository _securityCache;

    public PasswordAuthenticationStrategy(
            UserRepository userRepository,
            SecurityService securityService,
            SecurityCachingRepository securityCache) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        _securityCache = securityCache;
    }


    @Override
    public AuthenticateResponseDto execute(AuthenticateRequestDto request) throws InvalidApplicationException {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->  new AccessDeniedException("Username or password invalid"));

        var isPasswordValid = securityService.verifyPassword(request.getPassword(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new AccessDeniedException("Username or password invalid");
        }

        var generatedJwt = securityService.generateToken(user);
        var refreshToken = securityService.generateRefreshToken(user);

        _securityCache.storeRefreshToken(refreshToken, user);

        return new AuthenticateResponseDto(
                new UserDto(user),
                generatedJwt,
                refreshToken);
    }
}
