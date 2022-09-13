package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.AuthenticatedUserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequest;

import javax.management.InvalidApplicationException;

public interface AuthenticationService {
    AuthenticatedUserDto authenticateUser(AuthenticateRequest request) throws InvalidApplicationException;
}
