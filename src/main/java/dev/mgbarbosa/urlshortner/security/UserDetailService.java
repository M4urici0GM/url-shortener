package dev.mgbarbosa.urlshortner.security;

import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticatedUserDetails getUserByUsername(String username) {
        var maybeUser = userRepository.findByUsername(username);
        if (maybeUser.isEmpty()) {
            throw new UsernameNotFoundException("Could not find username " + username);
        }

        return new AuthenticatedUserDetails(maybeUser.get());
    }
}
