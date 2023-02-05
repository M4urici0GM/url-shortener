package dev.mgbarbosa.urlshortner.security;

import dev.mgbarbosa.urlshortner.services.interfaces.CustomUserDetailsService;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Responsible for reading, and validating token sent in Authorization Header.
 */
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final SecurityService securityService;
    private final CustomUserDetailsService userDetailService;

    public JwtSecurityFilter(SecurityService jwtUtils, CustomUserDetailsService userDetailService) {
        this.securityService = jwtUtils;
        this.userDetailService = userDetailService;
    }


    /**
     * Responsible for filtering and/or denying unauthorized http requests.
     * @param request HttpRequest
     * @param response HttpResponse
     * @param filterChain ??
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtStr = authHeader.substring(7);
        if (jwtStr.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        try {
            var ctx = SecurityContextHolder.getContext();
            var userClaims = securityService.extractUserClaims(jwtStr);
            var userDetails = userDetailService.getUserByUsername(userClaims.getUsername());

            var authToken = new AuthenticationToken(userDetails);

            if (ctx.getAuthentication() == null) {
                ctx.setAuthentication(authToken);
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
