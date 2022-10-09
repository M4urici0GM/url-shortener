package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.factories.interfaces.AuthenticationStrategyFactory;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import dev.mgbarbosa.urlshortner.strategies.authentication.AuthenticationStrategy;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.management.InvalidApplicationException;

@DisplayName("Authentication service tests")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
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

        Mockito.when(authStrategyFactory.create(ArgumentMatchers.anyString()))
                .thenReturn(strategyMock);

        Mockito.when(strategyMock.execute(ArgumentMatchers.any()))
                .thenReturn(new AuthenticateResponseDto());

        // Act
        var result = authenticationService.authenticateUser(request);

        // Assert
        Mockito.verify(authStrategyFactory, Mockito.times(1))
                .create(request.getGrantType());

        Mockito.verify(strategyMock, Mockito.times(1))
                .execute(ArgumentMatchers.eq(request));
    }

    @Test
    @DisplayName("isAuthenticated Should return true if user is authenticated")
    public void isAuthenticated_ShouldReturnTrueIfUserIsAuthenticated() {
        var ctx = SecurityContextHolder.createEmptyContext();
        var principal = new AuthenticatedUserDetails();
        principal.setId("632019a2c40a443d1ce69c3a");
        principal.setName("John Doe");
        principal.setUsername("john.doe123");
        principal.setEmail("john@doe.com");
        ctx.setAuthentication(new AuthenticationToken(principal));

        SecurityContextHolder.setContext(ctx);

        assert authenticationService.isAuthenticated();
    }
}
