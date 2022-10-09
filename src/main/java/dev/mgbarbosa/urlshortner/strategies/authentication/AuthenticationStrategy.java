package dev.mgbarbosa.urlshortner.strategies.authentication;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;

import javax.management.InvalidApplicationException;

public interface AuthenticationStrategy {
    AuthenticateResponseDto execute(AuthenticateRequestDto request) throws InvalidApplicationException, InvalidOperationException;
}
