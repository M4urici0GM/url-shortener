package dev.mgbarbosa.urlshortner.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailService userDetailService;

    public JwtFilter(JwtUtils jwtUtils, UserDetailService userDetailService) {
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtStr = authHeader.substring(7);
        if (jwtStr.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var ctx = SecurityContextHolder.getContext();
            var userClaims = jwtUtils.extractUserClaims(jwtStr);
            var userDetails = userDetailService.loadUserByUsername(userClaims.getUsername());

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            if (ctx.getAuthentication() == null) {
                ctx.setAuthentication(authToken);
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } finally {
            filterChain.doFilter(request, response);
        }

    }
}
