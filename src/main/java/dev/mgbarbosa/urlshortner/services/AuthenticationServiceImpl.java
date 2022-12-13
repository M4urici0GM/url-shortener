package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.factories.interfaces.AuthenticationStrategyFactory;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationStrategyFactory _authStrategyFactory;

    public AuthenticationServiceImpl(AuthenticationStrategyFactory authStrategyFactory) {
        _authStrategyFactory = authStrategyFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticateResponseDto authenticateUser(AuthenticateRequestDto request)
            throws
            InvalidOperationException,
            InvalidApplicationException {
        var strategy = _authStrategyFactory.create(request.getGrantType());
        return strategy.execute(request);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAuthenticated() {
        var ctx = SecurityContextHolder.getContext();
        if (ctx.getAuthentication().getClass().isAssignableFrom(AnonymousAuthenticationToken.class)) {
            return false;
        }

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
