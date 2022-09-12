package dev.mgbarbosa.urlshortner.services;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@DisplayName("SecurityService tests")
@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceImplTests {
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
}
