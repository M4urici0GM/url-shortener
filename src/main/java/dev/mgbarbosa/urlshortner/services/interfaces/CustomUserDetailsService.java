package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;

/**
 * Custom implementation of UserDetailsService
 */
public interface CustomUserDetailsService {
    AuthenticatedUserDetails getUserByUsername(String username);
}
