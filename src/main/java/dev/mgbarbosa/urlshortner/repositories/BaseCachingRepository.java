package dev.mgbarbosa.urlshortner.repositories;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * Base implementation for CachingRepository.
 * @param <Tid>
 * @param <TEntity>
 */
public abstract class BaseCachingRepository<Tid, TEntity> {
    private final RedisTemplate<Tid, TEntity> cache;

    public BaseCachingRepository(RedisTemplate<Tid, TEntity> cache) {
        this.cache = cache;
    }

    public Optional<TEntity> findByKey(Tid key) {
        try {
            var cacheValue = cache.opsForValue().get(key);
            if (cacheValue == null)
                return Optional.empty();

            return Optional.of(cacheValue);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public boolean saveByKey(Tid key, TEntity obj) {
        try {
            cache.opsForValue().set(key, obj);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
