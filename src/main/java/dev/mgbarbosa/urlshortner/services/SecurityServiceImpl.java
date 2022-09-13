package dev.mgbarbosa.urlshortner.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.mgbarbosa.urlshortner.config.JwtProperties;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.Instant;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final JwtProperties jwtProperties;;

    public SecurityServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String hashPassword(String input) {
        return BCrypt.hashpw(input, BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    @Override
    public AuthenticatedUserDetails extractUserClaims(String jwt) {
        var verifier = JWT.require(getAlgorithm())
                .withAudience(jwtProperties.getAudience())
                .withIssuer(jwtProperties.getIssuer())
                .withClaimPresence("email")
                .withClaimPresence("name")
                .withClaimPresence("username")
                .withClaimPresence("userId")
                .build();

        var decoded = verifier.verify(jwt);
        return new AuthenticatedUserDetails(
                decoded.getClaim("email").asString(),
                decoded.getClaim("name").asString(),
                decoded.getClaim("username").asString(),
                decoded.getClaim("userId").asString());
    }

    @Override
    public String generateToken(User user) throws InvalidApplicationException {
        return JWT.create()
                .withSubject(user.getEmail())
                .withAudience(jwtProperties.getAudience())
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(Instant.now())
                .withExpiresAt(getExpirationTime())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("username", user.getUsername())
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }

    private Instant getExpirationTime() throws InvalidApplicationException {
        if (this.jwtProperties.getExpirationTime() <= 0) {
            throw new InvalidApplicationException("Jwt Expiration time is required.");
        }

        return Instant.now().plusSeconds(jwtProperties.getExpirationTime());
    }
}
