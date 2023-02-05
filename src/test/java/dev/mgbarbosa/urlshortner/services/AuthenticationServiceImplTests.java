package dev.mgbarbosa.urlshortner.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.factories.interfaces.AuthenticationStrategyFactory;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import dev.mgbarbosa.urlshortner.strategies.authentication.AuthenticationStrategy;
import java.util.UUID;
import javax.management.InvalidApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
@DisplayName("Authentication service tests")
public class AuthenticationServiceImplTests {

    @Mock
    AuthenticationStrategyFactory authStrategyFactory;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Test
    @DisplayName("authenticateUser Should call factory correctly")
    public void authenticateUser_ShouldCallFactoryCorrectly() throws InvalidOperationException, InvalidApplicationException {
        // Arrange
        var request = new AuthenticateRequestDto("m4urici0gm", "blueScreen#666", "password");
        var strategyMock = Mockito.mock(AuthenticationStrategy.class);

        when(authStrategyFactory.create(ArgumentMatchers.anyString()))
                .thenReturn(strategyMock);

        when(strategyMock.execute(ArgumentMatchers.any()))
                .thenReturn(new AuthenticateResponseDto());

        // Act
        authenticationService.authenticateUser(request);

        // Assert
        verify(authStrategyFactory, Mockito.times(1))
                .create(request.getGrantType());

        verify(strategyMock, Mockito.times(1))
                .execute(ArgumentMatchers.eq(request));
    }

    @Test
    @DisplayName("isAuthenticated should return true if user is not authenticated")
    public void isAuthenticated_shouldReturnTrue_whenUserIsNotAuthenticated() {
        assertFalse(authenticationService.isAuthenticated());
    }

    @Test
    @DisplayName("isAuthenticated Should return true if user is authenticated")
    public void isAuthenticated_ShouldReturnTrueIfUserIsAuthenticated() {
        var ctx = SecurityContextHolder.createEmptyContext();
        var principal = new AuthenticatedUserDetails();
        principal.setId(UUID.randomUUID());
        principal.setName("John Doe");
        principal.setUsername("john.doe123");
        principal.setEmail("john@doe.com");
        ctx.setAuthentication(new AuthenticationToken(principal));

        SecurityContextHolder.setContext(ctx);

        assert authenticationService.isAuthenticated();
    }
}
