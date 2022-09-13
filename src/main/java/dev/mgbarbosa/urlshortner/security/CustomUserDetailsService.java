package dev.mgbarbosa.urlshortner.security;

public interface CustomUserDetailsService {
    AuthenticatedUserDetails getUserByUsername(String username);
}
