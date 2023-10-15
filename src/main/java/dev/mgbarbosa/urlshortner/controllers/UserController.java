package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.PaginatedRequest;
import dev.mgbarbosa.urlshortner.dtos.responses.PaginatedResponse;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;
import dev.mgbarbosa.urlshortner.services.interfaces.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@Valid @RequestBody UserDto request) throws EntityExists {
        return userService.createUser(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    PaginatedResponse<UserDto> findAll(@Valid PaginatedRequest request) {
         return userService.getUsers(request);
    }
}
