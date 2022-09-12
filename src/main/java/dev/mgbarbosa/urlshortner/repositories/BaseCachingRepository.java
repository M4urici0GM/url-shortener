//package dev.mgbarbosa.urlshortner.repositories;
//
//import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.Optional;
//
//public abstract class BaseCachingRepository<Tid, TEntity> {
//    private final RedisTemplate<Tid, TEntity> cache;
//
//    public BaseCachingRepository(RedisTemplate<Tid, TEntity> cache) {
//        this.cache = cache;
//    }
//
//    public Optional<TEntity> findByKey(Tid key) {
//
//    }
//
//}
