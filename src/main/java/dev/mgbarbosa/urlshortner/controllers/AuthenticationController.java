package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.AccessDeniedException;
import javax.management.InvalidApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<AuthenticateResponseDto> authenticateUser(@Valid @RequestBody AuthenticateRequestDto request)
      throws URISyntaxException, InvalidApplicationException, InvalidOperationException {
    final var result = authenticationService.authenticateUser(request);
    return ResponseEntity
        .created(new URI(""))
        .body(result);
  }

  /**
   * Get user profile, containing user details + profile info, such as roles.
   */
  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<AuthenticatedUserDetails> getProfile() throws AccessDeniedException {
    var user = authenticationService.getAuthenticatedUser();
    return ResponseEntity
        .ok()
        .body(user);
  }
}
