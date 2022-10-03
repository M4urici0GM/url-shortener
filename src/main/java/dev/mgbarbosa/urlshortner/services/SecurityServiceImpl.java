package dev.mgbarbosa.urlshortner.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.mgbarbosa.urlshortner.config.JwtProperties;
import dev.mgbarbosa.urlshortner.dtos.UserClaim;
import dev.mgbarbosa.urlshortner.dtos.responses.JwtToken;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final JwtProperties jwtProperties;

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

    /**
     * Creates a token for the specified user.
     * @param user the user which the token will be issued to.
     * @return the created token
     * @throws InvalidApplicationException internal JWT exception.
     */
    @Override
    public JwtToken generateToken(User user) throws InvalidApplicationException {
        var expirationDate = getDefaultJwtExpirationTime();
        var claimList = Stream.of(
                        new UserClaim("userId", user.getId()),
                        new UserClaim("name", user.getName()),
                        new UserClaim("email", user.getEmail()),
                        new UserClaim("username", user.getUsername())
                )
                .toList();


        return generateToken(claimList, expirationDate);
    }

    /**
     * Creates new JwtToken with specified claims
     *
     * Note that by default it'll add the following claims:
     * Iss: Time that token was created.
     * Exi: Time that token will be expired.
     * Audience: Who's this token is for.
     * Issuer: Who issued the token (This app)
     *
     * @param userClaims the claims list to be added to the token
     * @param expiresAt the time token will be expired.
     * @return the created jwt token
     * @throws InvalidApplicationException internal JWT exception.
     */
    @Override
    public JwtToken generateToken(List<UserClaim> userClaims, Instant expiresAt) throws InvalidApplicationException {
        var now = Instant.now();
        var jwt = JWT
                .create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiresAt)
                .withAudience(jwtProperties.getAudience())
                .withIssuer(jwtProperties.getIssuer());

        userClaims.forEach((claim) -> jwt.withClaim(claim.getName(), claim.getValue()));
        return new JwtToken(
                jwt.sign(getAlgorithm()),
                now,
                expiresAt);

    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public Instant getDefaultJwtExpirationTime() throws InvalidApplicationException {
        if (this.jwtProperties.getExpirationTime() <= 0) {
            throw new InvalidApplicationException("Jwt Expiration time is required.");
        }

        return Instant.now().plusSeconds(jwtProperties.getExpirationTime());
    }
}
