package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.AuthenticatedUserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequest;

public interface AuthenticationService {
    AuthenticatedUserDto authenticateUser(AuthenticateRequest request);
}
