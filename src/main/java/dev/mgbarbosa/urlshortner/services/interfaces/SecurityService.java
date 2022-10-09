package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.responses.JwtToken;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.dtos.Claim;

import javax.management.InvalidApplicationException;
import java.time.Instant;
import java.util.List;

/**
 * Service responsible for security related tasks
 */
public interface SecurityService {
    String hashPassword(String input);
    boolean verifyPassword(String password, String hash);
    AuthenticatedUserDetails extractUserClaims(String jwt);
    JwtToken generateToken(User user) throws InvalidApplicationException;
    JwtToken generateRefreshToken(User user) throws InvalidApplicationException;
    JwtToken generateToken(List<Claim> userClaims, Instant expiresAt) throws InvalidApplicationException;
}
