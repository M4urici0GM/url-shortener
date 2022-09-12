package dev.mgbarbosa.urlshortner.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.mgbarbosa.urlshortner.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .withExpiresAt(LocalDateTime.now().plusSeconds(expirationTime).toInstant(ZoneOffset.UTC))
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withClaim("username", user.getUsername())
                .sign(getAlgorithm());
    }

    public UserClaims extractUserClaims(String jwt) throws JWTVerificationException {
        var verifier = JWT.require(getAlgorithm())
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaimPresence("email")
                .withClaimPresence("name")
                .withClaimPresence("username")
                .withClaimPresence("id")
                .build();

        var decoded = verifier.verify(jwt);
        return new UserClaims(
                decoded.getClaim("email").asString(),
                decoded.getClaim("name").asString(),
                decoded.getClaim("username").asString(),
                decoded.getClaim("id").asString());
    }


    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
