package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.PaginatedRequest;
import dev.mgbarbosa.urlshortner.dtos.PaginatedResponse;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import dev.mgbarbosa.urlshortner.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto request) throws EntityExists, URISyntaxException {
        var user = userService.createUser(request);
        return ResponseEntity.created(new URI("")).body(user);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<PaginatedResponse<UserDto>> findAll(@Valid PaginatedRequest request) {
        var users = userService.getUsers(request);
        return ResponseEntity.ok().body(users);
    }
}
