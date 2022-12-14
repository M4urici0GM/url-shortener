package dev.mgbarbosa.urlshortner.factories;

import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.factories.interfaces.AuthenticationStrategyFactory;
import dev.mgbarbosa.urlshortner.strategies.authentication.AuthenticationStrategy;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationStrategyFactoryImpl implements AuthenticationStrategyFactory {
    private final Map<String, AuthenticationStrategy> _availableStrategies;

    public AuthenticationStrategyFactoryImpl(Map<String, AuthenticationStrategy> availableStrategies) {
        _availableStrategies = availableStrategies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationStrategy create(String method) throws InvalidOperationException {
        var strategyName = getAuthenticationStrategyName(method);
        return Optional
            .of(_availableStrategies.get(strategyName))
            .orElseThrow(InvalidOperationException::new);
    }

    private String getAuthenticationStrategyName(String grantType) throws InvalidOperationException {
        return switch (grantType.toLowerCase()) {
            case "password" -> "passwordAuthenticationStrategy";
            case "refresh-token"  -> "refreshTokenAuthenticationStrategy";
            default -> throw new InvalidOperationException();
        };
    }
}
