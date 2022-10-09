package dev.mgbarbosa.urlshortner.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.javafaker.Faker;
import dev.mgbarbosa.urlshortner.config.JwtProperties;
import dev.mgbarbosa.urlshortner.dtos.Claim;
import dev.mgbarbosa.urlshortner.entities.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.management.InvalidApplicationException;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@DisplayName("SecurityService tests")
@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceImplTests {

    @Mock
    JwtProperties jwtProperties;

    @InjectMocks
    private SecurityServiceImpl securityService;

    @Test
    @DisplayName("Should hash correctly")
    public void shouldHashCorrectly() {
        // Arrange
        var password = "test-password";

        // Act
        var hash = securityService.hashPassword(password);

        // Assert
        assert hash.length() > 0;
    }

    @Test
    @DisplayName("Should verify password correctly")
    public void shouldComparePasswordCorrectly() {
        // Arrange
        var password = "some-password";
        var hash = BCrypt.hashpw(password, BCrypt.gensalt());

        // Act
        var result = securityService.verifyPassword(password, hash);

        // Assert
        assert result;
    }

    @Test
    @DisplayName("Should retrieve expected info from token")
    public void shouldExtractUserInfoFromToken() {

    }

    @Test(expected = InvalidApplicationException.class)
    @DisplayName("Should throw if expiration time is not set, or below 0")
    public void shouldThrowIfExpirationTimeNotSet() throws InvalidApplicationException {
        // Arrange
        var faker = new Faker();
        var user = new User(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.internet().avatar(),
                "SOME_PASS_HASH");

        user.setId("SOME_ID");

        // act
        securityService.generateToken(user);
    }

    @Test
    @DisplayName("Should extract user information from token")
    public void shouldExtractUserInformationFromToken() {
        // Arrange
        var secretValue = "SOME_SECRET";
        var audienceValue = "SOME_AUDIENCE";
        var issuerValue = "SOME_ISSUER";
        var faker = new Faker();
        var user = new User(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.internet().avatar(),
                "SOME_PASS_HASH");

        user.setId("SOME_ID");

        Mockito.when(jwtProperties.getIssuer()).thenReturn(issuerValue);
        Mockito.when(jwtProperties.getAudience()).thenReturn(audienceValue);
        Mockito.when(jwtProperties.getSecret()).thenReturn(secretValue);

        var jwt = JWT.create()
                .withAudience(audienceValue)
                .withIssuer(issuerValue)
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256(secretValue));

        // Act
        var result = securityService.extractUserClaims(jwt);

        // Assert
        assert result.getId().equals(user.getId());
        assert result.getName().equals(user.getName());
        assert result.getEmail().equals(user.getEmail());
        assert result.getUsername().equals(user.getUsername());
    }


    @Test
    @DisplayName("Should generate user token with expected claims")
    public void shouldGenerateUserTokenCorrectly() throws InvalidApplicationException {
        // Assert
        var secretValue = "SOME_SECRET";
        var audienceValue = "SOME_AUDIENCE";
        var issuerValue = "SOME_ISSUER";
        var faker = new Faker();
        var now = Instant.now();

        var user = new User(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.internet().avatar(),
                "SOME_PASS_HASH");
        user.setId("SOME_ID");

        Mockito.when(jwtProperties.getIssuer()).thenReturn(issuerValue);
        Mockito.when(jwtProperties.getAudience()).thenReturn(audienceValue);
        Mockito.when(jwtProperties.getSecret()).thenReturn(secretValue);
        Mockito.when(jwtProperties.getExpirationTime()).thenReturn((long) 3600);

        // Act
        var result = securityService.generateToken(user);

        // Assert
        var jwtVerifier = JWT.require(Algorithm.HMAC256(secretValue)).build();
        var decoded = jwtVerifier.verify(result.getToken());
        var claims = decoded.getClaims();

        assert checkClaim(claims, "userId", user.getId());
        assert checkClaim(claims, "name", user.getName());
        assert checkClaim(claims, "email", user.getEmail());
        assert checkClaim(claims, "username", user.getUsername());
    }

    @Test
    @DisplayName("Should generate token with expected claims")
    public void shouldGenerateTokenCorrectly() throws InvalidApplicationException {
        // Arrange
        var secretValue = "SOME_SECRET";
        var audienceValue = "SOME_AUDIENCE";
        var issuerValue = "SOME_ISSUER";
        var now = Instant.now();

        var expirationTime = now.plusSeconds(3600);
        var expectedClaims = Stream.of(
                new Claim("CLAIM1", "CLAIM1_VALUE"),
                new Claim("CLAIM2", "CLAIM1_VALUE2")).toList();

        Mockito.when(jwtProperties.getIssuer()).thenReturn(issuerValue);
        Mockito.when(jwtProperties.getAudience()).thenReturn(audienceValue);
        Mockito.when(jwtProperties.getSecret()).thenReturn(secretValue);

        // Act
        var result = securityService.generateToken(expectedClaims, expirationTime);

        // Assert
        var jwtVerifier = JWT.require(Algorithm.HMAC256(secretValue)).build();
        var decoded = jwtVerifier.verify(result.getToken());
        var claims = decoded.getClaims();

        assert Objects.equals(expirationTime, result.getExpiresAt());
        assert expectedClaims.stream().allMatch(claim -> claims.keySet().stream().anyMatch(x -> x == claim.getName()));
        assert claims.get("exp").asInstant().getEpochSecond() == expirationTime.getEpochSecond();
        assert claims.get("aud").asString().equals(audienceValue);
        assert claims.get("iss").asString().equals(issuerValue);
    }

    private boolean checkClaim(Map<String, com.auth0.jwt.interfaces.Claim> claims, String claimName, String expectedValue) {
        var contains = claims.keySet().stream().anyMatch(x -> x.equals(claimName));
        if (!contains)
            return false;

        return claims
                .get(claimName)
                .asString()
                .equals(expectedValue);
    }
}
