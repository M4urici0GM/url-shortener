package dev.mgbarbosa.urlshortner.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Represents an Authenticated user's credential.
 * Used to store user's info to be accessed across the system.
 */
public class AuthenticationToken extends AbstractAuthenticationToken {
    private AuthenticatedUserDetails principal;

    public AuthenticationToken() {
        super(null);
    }

    public AuthenticationToken(AuthenticatedUserDetails userDetails) {
        this();
        this.principal = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
