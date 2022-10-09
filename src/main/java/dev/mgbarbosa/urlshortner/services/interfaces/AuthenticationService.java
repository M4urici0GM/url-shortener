package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;

import javax.management.InvalidApplicationException;

public interface AuthenticationService {
    /**
     * Checks whether user is authenticated or not.
     */
    boolean isAuthenticated();

    /**
     * Gets authenticated user details from SecurityContext.
     */
    AuthenticatedUserDetails getAuthenticatedUser();

    /**
     * Tries to authenticate user with username/password.
     * @param request Authenticate request containing user credentials.
     * @return AuthenticatedResponseDto containing user details and jwt token.
     * @throws InvalidApplicationException throws if jwt creation fails.
     */
    AuthenticateResponseDto authenticateUser(AuthenticateRequestDto request) throws InvalidApplicationException, InvalidOperationException;
}
