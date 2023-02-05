package dev.mgbarbosa.urlshortner.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.mgbarbosa.urlshortner.config.JwtProperties;
import dev.mgbarbosa.urlshortner.dtos.Claim;
import dev.mgbarbosa.urlshortner.dtos.responses.JwtToken;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.management.InvalidApplicationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final JwtProperties jwtProperties;
    private final List<String> privateClaimNames = Stream.of(
            "jti",
            "iss",
            "aud",
            "exp",
            "iat").toList();

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
                UUID.fromString(decoded.getClaim("userId").asString()));
    }

    /**
     * Creates a token for the specified user.
     *
     * @param user the user which the token will be issued to.
     * @return the created token
     * @throws InvalidApplicationException internal JWT exception.
     */
    @Override
    public JwtToken generateToken(User user) throws InvalidApplicationException {
        var expirationDate = getDefaultJwtExpirationTime();
        var claimList = Stream.of(
                        new Claim("userId", user.getId().toString()),
                        new Claim("name", user.getName()),
                        new Claim("email", user.getEmail()),
                        new Claim("username", user.getUsername())
                )
                .toList();


        return generateToken(claimList, expirationDate);
    }

    @Override
    public JwtToken generateRefreshToken(User user) throws InvalidApplicationException {
        var expirationSeconds = jwtProperties.getRefreshTokenExpirationTime();
        var expirationDate = Instant.now().plusSeconds(expirationSeconds);
        var claimList = Stream.of(
                new Claim("email", user.getEmail()),
                new Claim("userId", user.getId().toString()),
                new Claim("scope", "refresh-token")).toList();

        return generateToken(claimList, expirationDate);
    }

    /**
     * Creates new JwtToken with specified claims
     * <p>
     * Note that by default it'll add the following claims:
     * Iss: Time that token was created.
     * Exi: Time that token will be expired.
     * Audience: Who's this token is for.
     * Issuer: Who issued the token (This app)
     *
     * @param claims    the claims list to be added to the token
     * @param expiresAt the time token will be expired.
     * @return the created jwt token
     */
    @Override
    public JwtToken generateToken(List<Claim> claims, Instant expiresAt) {
        var now = Instant.now();
        var tokenIdentifier = UUID.randomUUID();

        var jwtBuilder = JWT
                .create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(expiresAt)
                .withAudience(jwtProperties.getAudience())
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("jti", tokenIdentifier.toString());

        claims.stream()
                .filter(x -> !privateClaimNames.contains(x.getName()))
                .forEach((claim) -> jwtBuilder.withClaim(claim.getName(), claim.getValue()));

        return JwtToken.builder()
            .token(jwtBuilder.sign(getAlgorithm()))
            .createdAt(now)
            .expiresAt(expiresAt)
            .build();
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
