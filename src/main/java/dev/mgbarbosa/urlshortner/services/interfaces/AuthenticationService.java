package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;

import javax.management.InvalidApplicationException;
import java.util.Optional;

public interface AuthenticationService {
    boolean isAuthenticated();
    AuthenticatedUserDetails getAuthenticatedUser();
    AuthenticateResponseDto authenticateUser(AuthenticateRequestDto request) throws InvalidApplicationException;
}
