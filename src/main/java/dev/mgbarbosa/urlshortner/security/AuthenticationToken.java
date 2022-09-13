package dev.mgbarbosa.urlshortner.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private AuthenticatedUserDetails principal;

    public AuthenticationToken() {
        super((Collection) null);
    }

    public AuthenticationToken(AuthenticatedUserDetails userDetails) {
        this();
        this.principal = userDetails;
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
