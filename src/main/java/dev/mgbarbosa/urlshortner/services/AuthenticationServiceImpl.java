package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.AuthenticatedUserDto;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequest;
import dev.mgbarbosa.urlshortner.exceptios.InvalidCredentialsException;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final SecurityService securityService;;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(
            SecurityService securityService,
            UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }


    @Override
    public AuthenticatedUserDto authenticateUser(AuthenticateRequest request) throws InvalidApplicationException {
        var maybeUser = userRepository.findByUsername(request.getUsername());
        if (!maybeUser.isPresent()) {
            throw new InvalidCredentialsException("Username or password invalid");
        }

        var user = maybeUser.get();
        var isPasswordValid = securityService.verifyPassword(request.getPassword(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException("Username or password invalid");
        }

        var generatedJwt = securityService.generateToken(user);
        return new AuthenticatedUserDto(
                new UserDto(user),
                generatedJwt);
    }
}
