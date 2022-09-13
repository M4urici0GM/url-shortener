package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.AuthenticatedUserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InvalidApplicationException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Tries to authenticate user with username and password.
     * @param request Request containing username and password
     * @return returns object containing userDetails + jwtToken
     */
    @PostMapping
    public ResponseEntity<AuthenticatedUserDto> authenticateUser(
            @Valid @RequestBody AuthenticateRequest request) throws URISyntaxException, InvalidApplicationException {

        var result = authenticationService.authenticateUser(request);
        return ResponseEntity.created(new URI("")).body(result);
    }
}
