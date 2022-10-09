package dev.mgbarbosa.urlshortner.factories;

import dev.mgbarbosa.urlshortner.annotations.WithUserMock;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class SecurityContextMockFactory implements WithSecurityContextFactory<WithUserMock> {
    @Override
    public SecurityContext createSecurityContext(WithUserMock annotation) {
        var ctx = SecurityContextHolder.createEmptyContext();
        var principal = new AuthenticatedUserDetails();
        principal.setId(annotation.id());
        principal.setName(annotation.name());
        principal.setUsername(annotation.username());
        principal.setEmail(annotation.email());

        ctx.setAuthentication(new AuthenticationToken(principal));

        SecurityContextHolder.setContext(ctx);
        return ctx;
    }
}
