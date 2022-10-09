package dev.mgbarbosa.urlshortner.repositories.interfaces;

import dev.mgbarbosa.urlshortner.dtos.responses.JwtToken;
import dev.mgbarbosa.urlshortner.entities.User;

public interface SecurityCachingRepository {
    void storeRefreshToken(JwtToken token, User user);
}
