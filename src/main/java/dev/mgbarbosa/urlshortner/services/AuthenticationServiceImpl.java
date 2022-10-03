package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.Instant;
import java.util.ArrayList;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final SecurityService securityService;;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(
            SecurityService securityService,
            UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticateResponseDto authenticateUser(AuthenticateRequestDto request)
            throws
            InvalidApplicationException {

        var maybeUser = userRepository.findByUsername(request.getUsername());
        var user = maybeUser.orElseThrow(() ->  new AccessDeniedException("Username or password invalid"));

        var isPasswordValid = securityService.verifyPassword(request.getPassword(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new AccessDeniedException("Username or password invalid");
        }

        var generatedJwt = securityService.generateToken(user);
        var refreshToken = securityService.generateToken(new ArrayList<>(), Instant.now().plusSeconds(3600));

        return new AuthenticateResponseDto(
                new UserDto(user),
                generatedJwt,
                refreshToken);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAuthenticated() {
        var ctx = SecurityContextHolder.getContext();
        return ctx
                .getAuthentication()
                .isAuthenticated();
    }

    /**
     * {@inheritDoc}
     */
    public AuthenticatedUserDetails getAuthenticatedUser() throws AccessDeniedException {
        var ctx = SecurityContextHolder.getContext();
        if (!(ctx.getAuthentication() instanceof AuthenticationToken)) {
            throw new AccessDeniedException("User not authenticated");
        }

        return (AuthenticatedUserDetails) ctx.getAuthentication().getPrincipal();
    }
}
