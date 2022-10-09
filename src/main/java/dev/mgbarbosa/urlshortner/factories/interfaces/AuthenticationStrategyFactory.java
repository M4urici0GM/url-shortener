package dev.mgbarbosa.urlshortner.factories.interfaces;


import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.strategies.authentication.AuthenticationStrategy;

public interface AuthenticationStrategyFactory {
    /**
     * Creates new AuthenticationStrategy instance.
     * @param method what kind of authentication method should be used.
     * @return the requested authentication strategy
     */
    AuthenticationStrategy create(String method) throws InvalidOperationException;
}
