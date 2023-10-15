package dev.mgbarbosa.urlshortner.strategies.authentication;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import javax.management.InvalidApplicationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component("PasswordAuthenticationStrategy")
public class PasswordAuthenticationStrategy implements AuthenticationStrategy {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public PasswordAuthenticationStrategy(
            UserRepository userRepository,
            SecurityService securityService
    ) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public AuthenticateResponseDto execute(AuthenticateRequestDto request) throws InvalidApplicationException {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->  new AccessDeniedException("Invalid username or password"));

        var isPasswordValid = securityService.verifyPassword(request.getPassword(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new AccessDeniedException("Invalid username or password");
        }

        var generatedJwt = securityService.generateToken(user);
        var refreshToken = securityService.generateRefreshToken(user);

        return new AuthenticateResponseDto(
                new UserDto(user),
                generatedJwt,
                refreshToken);
    }
}
