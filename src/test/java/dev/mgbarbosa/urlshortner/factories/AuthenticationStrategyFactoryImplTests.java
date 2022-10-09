package dev.mgbarbosa.urlshortner.factories;

import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import dev.mgbarbosa.urlshortner.strategies.authentication.PasswordAuthenticationStrategy;
import dev.mgbarbosa.urlshortner.strategies.authentication.RefreshTokenAuthenticationStrategy;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

@DisplayName("Authentication Strategy Factory Service Tests")
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationStrategyFactoryImplTests {

    @Mock
    SecurityCachingRepository securityCachingRepository;

    @Mock
    SecurityService securityService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthenticationStrategyFactoryImpl authStrategyPattern;

    @Test
    @DisplayName("Should create correct object")
    public void shouldCreateCorrectClass() throws InvalidOperationException {
        // Arrange
        var type = new HashMap<String, Class<?>>();
        type.put("refreshToken", RefreshTokenAuthenticationStrategy.class);
        type.put("password", PasswordAuthenticationStrategy.class);


        for (String key : type.keySet()) {
            var expectedValue = type.get(key);

            // Act
            var result = authStrategyPattern.create(key);

            // Result
            assert result.getClass().isAssignableFrom(expectedValue);
        }
    }

    @Test(expected = InvalidOperationException.class)
    @DisplayName("Should throw if type is invalid")
    public void shouldThrowIfValueIsNotValid() throws InvalidOperationException {
        // Arrange
        var type = "invalid-type";

        // Act
        authStrategyPattern.create(type);
    }
}
