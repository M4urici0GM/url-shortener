package dev.mgbarbosa.urlshortner.factories;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.strategies.authentication.PasswordAuthenticationStrategy;
import dev.mgbarbosa.urlshortner.strategies.authentication.RefreshTokenAuthenticationStrategy;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("Authentication Strategy Factory Service Tests")
@SpringBootTest(webEnvironment = NONE)
public class AuthenticationStrategyFactoryImplTests {

    @MockBean
    PasswordAuthenticationStrategy passwordAuthenticationStrategy;

    @MockBean
    RefreshTokenAuthenticationStrategy refreshTokenAuthenticationStrategy;

    @InjectMocks
    AuthenticationStrategyFactoryImpl authStrategyPattern;

    @Test
    @DisplayName("Should create correct object")
    public void shouldCreateCorrectClass() throws InvalidOperationException {
        // Arrange
        var type = new HashMap<String, Class<?>>();
        type.put("refresh-token", RefreshTokenAuthenticationStrategy.class);
        type.put("password", PasswordAuthenticationStrategy.class);


        for (String key : type.keySet()) {
            var expectedValue = type.get(key);

            // Act
            var result = authStrategyPattern.create(key);

            // Result
            assert result.getClass().isAssignableFrom(expectedValue);
        }
    }

    @Test
    @DisplayName("Should throw if type is invalid")
    public void shouldThrowIfValueIsNotValid() {
        // Arrange
        var type = "invalid-type";

        // Act
        assertThrows(InvalidOperationException.class, () -> authStrategyPattern.create(type));
    }
}
