package dev.mgbarbosa.urlshortner.factories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.strategies.authentication.PasswordAuthenticationStrategy;
import dev.mgbarbosa.urlshortner.strategies.authentication.RefreshTokenAuthenticationStrategy;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("Authentication Strategy Factory Service Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = AuthenticationStrategyFactoryImpl.class)
@ExtendWith(SpringExtension.class)
public class AuthenticationStrategyFactoryImplTests {

    @Autowired
    AuthenticationStrategyFactoryImpl authStrategyPattern;

    @MockBean(name = "PasswordAuthenticationStrategy")
    PasswordAuthenticationStrategy passwordAuthenticationStrategy;

    @MockBean(name = "RefreshTokenAuthenticationStrategy")
    RefreshTokenAuthenticationStrategy refreshTokenAuthenticationStrategy;

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
             Assertions.assertTrue(expectedValue.isAssignableFrom(AopUtils.getTargetClass(result)));
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
