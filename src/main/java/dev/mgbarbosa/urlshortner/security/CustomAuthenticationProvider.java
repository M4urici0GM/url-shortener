package dev.mgbarbosa.urlshortner.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * Method implementation to satisfy PreAuthorize.
     * At this point JwtFilter would already have validated user's Jwt token.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    /**
     * Forces app to only use AuthenticationToken
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
