package dev.mgbarbosa.urlshortner.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.mgbarbosa.urlshortner.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration-seconds}")
    private long expirationTime;

    public String generateToken(User user) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(user.getEmail())
                .withAudience(audience)
                .withIssuer(issuer)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(expirationTime))
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("username", user.getUsername())
                .sign(getAlgorithm());
    }

    public AuthenticatedUserDetails extractUserClaims(String jwt) throws JWTVerificationException {
        var verifier = JWT.require(getAlgorithm())
                .withAudience(audience)
                .withIssuer(issuer)
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


    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
