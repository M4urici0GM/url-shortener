package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param request Request containing username and password
     * @return returns object containing userDetails + jwtToken
     */
    @PostMapping
    public ResponseEntity<AuthenticateResponseDto> authenticateUser(
            @Valid @RequestBody AuthenticateRequestDto request) throws URISyntaxException, InvalidApplicationException {

        var result = authenticationService.authenticateUser(request);
        return ResponseEntity.created(new URI("")).body(result);
    }

    @GetMapping("/profile")
    ResponseEntity<AuthenticatedUserDetails> getProfile() {
        var user = authenticationService.getAuthenticatedUser();
        return ResponseEntity
                .ok()
                .body(user);
    }
}
