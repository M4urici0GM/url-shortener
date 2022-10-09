package dev.mgbarbosa.urlshortner.factories;

import dev.mgbarbosa.urlshortner.exceptios.InvalidOperationException;
import dev.mgbarbosa.urlshortner.factories.interfaces.AuthenticationStrategyFactory;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import dev.mgbarbosa.urlshortner.strategies.authentication.AuthenticationStrategy;
import dev.mgbarbosa.urlshortner.strategies.authentication.PasswordAuthenticationStrategy;
import dev.mgbarbosa.urlshortner.strategies.authentication.RefreshTokenAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationStrategyFactoryImpl implements AuthenticationStrategyFactory {
    private final SecurityCachingRepository _securityCacheRepository;
    private final SecurityService _securityService;
    private final UserRepository _userRepository;

    public AuthenticationStrategyFactoryImpl(
            SecurityCachingRepository securityCacheRepository,
            SecurityService securityService,
            UserRepository userRepository) {
        _securityCacheRepository = securityCacheRepository;
        _securityService = securityService;
        _userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationStrategy create(String method) throws InvalidOperationException {
        switch (method.toLowerCase()) {
            case "refresh-token":
            case "refreshtoken":
                return new RefreshTokenAuthenticationStrategy(_securityCacheRepository);
            case "password":
                return new PasswordAuthenticationStrategy(_userRepository, _securityService, _securityCacheRepository);
            default:
                throw new InvalidOperationException();
        }
    }
}
