package dev.mgbarbosa.urlshortner.repositories;

import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Base implementation for CachingRepository.
 * @param <TKey>
 * @param <TEntity>
 */
public abstract class BaseCachingRepository<TKey, TEntity> {
    private final RedisTemplate<TKey, TEntity> cache;

    public BaseCachingRepository(RedisTemplate<TKey, TEntity> cache) {
        this.cache = cache;
    }

    public Optional<TEntity> findByKey(TKey key) {
        try {
            var cacheValue = cache.opsForValue().get(key);
            if (cacheValue == null)
                return Optional.empty();

            return Optional.of(cacheValue);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public boolean saveByKey(TKey key, TEntity obj) {
        try {
            cache.opsForValue().set(key, obj);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
