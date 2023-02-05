package dev.mgbarbosa.urlshortner.repositories;

import dev.mgbarbosa.urlshortner.dtos.SecurityTokenTicket;
import dev.mgbarbosa.urlshortner.dtos.responses.JwtToken;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.repositories.interfaces.SecurityCachingRepository;
import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SecurityCachingRepositoryImpl extends BaseCachingRepository<UUID, SecurityTokenTicket> implements SecurityCachingRepository {
    public SecurityCachingRepositoryImpl(RedisTemplate<UUID, SecurityTokenTicket> cache) {
        super(cache);
    }


    @Override
    public void storeRefreshToken(JwtToken token, User user) {

    }
}
