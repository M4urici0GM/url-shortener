package dev.mgbarbosa.urlshortner.repositories;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CachingShortedUrlRepositoryImpl
            extends BaseCachingRepository<String, ShortenedUrl>
            implements CachingShortedUrlRepository {

    private final RedisTemplate<String, ShortenedUrl> cache;

    public CachingShortedUrlRepositoryImpl(RedisTemplate<String, ShortenedUrl> cache) {
        super(cache);
        this.cache = cache;
    }

    @Override
    public Optional<ShortenedUrl> findById(String id) {
        return this.findByKey(id);
    }

    @Override
    public Optional<ShortenedUrl> findByShortVersion(String id) {
        return this.findByKey(id);
    }

    @Override
    public boolean saveById(ShortenedUrl shortenedUrl) {
        return this.saveByKey(shortenedUrl.getId(), shortenedUrl);
    }

    @Override
    public boolean saveByShortVersion(ShortenedUrl shortenedUrl) {
        return this.saveByKey(shortenedUrl.getShortenedVersion(), shortenedUrl);
    }
}
