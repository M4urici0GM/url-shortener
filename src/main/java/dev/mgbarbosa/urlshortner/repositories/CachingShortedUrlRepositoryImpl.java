package dev.mgbarbosa.urlshortner.repositories;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CachingShortedUrlRepositoryImpl implements CachingShortedUrlRepository {

    private final RedisTemplate<String, ShortenedUrl> cache;

    public CachingShortedUrlRepositoryImpl(RedisTemplate<String, ShortenedUrl> cache) {
        this.cache = cache;
    }

    @Override
    public Optional<ShortenedUrl> findById(String id) {
        try {
            var cacheValue = cache.opsForValue().get(id);
            return Optional.of(cacheValue);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ShortenedUrl> findByShortVersion(String id) {
        return Optional.empty();
    }

    @Override
    public boolean saveById(ShortenedUrl shortenedUrl) {
        try {
            cache.opsForValue().set(shortenedUrl.getId(), shortenedUrl);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean saveByShortVersion(ShortenedUrl shortenedUrl) {
        return false;
    }
}
