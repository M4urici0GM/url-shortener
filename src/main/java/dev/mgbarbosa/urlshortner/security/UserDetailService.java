package dev.mgbarbosa.urlshortner.security;

import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Tries to get user based on the username.
     *
     * @param username The username to be searched for.
     * @return The requested user.
     * @throws UsernameNotFoundException throws if user is not found.
     */
    @Override
    public AuthenticatedUserDetails getUserByUsername(String username) {
        var maybeUser = userRepository.findByUsername(username);
        if (maybeUser.isEmpty()) {
            throw new UsernameNotFoundException("Could not find username " + username);
        }

        return new AuthenticatedUserDetails(maybeUser.get());
    }
}
