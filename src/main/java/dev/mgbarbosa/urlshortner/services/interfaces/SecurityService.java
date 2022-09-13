package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;

import javax.management.InvalidApplicationException;

public interface SecurityService {
    String hashPassword(String input);
    boolean verifyPassword(String password, String hash);
    AuthenticatedUserDetails extractUserClaims(String jwt);
    String generateToken(User user) throws InvalidApplicationException;
}
