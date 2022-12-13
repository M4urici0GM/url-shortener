package dev.mgbarbosa.urlshortner.repositories.interfaces;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShortedUrlRepository extends PagingAndSortingRepository<ShortenedUrl, String>, CrudRepository<ShortenedUrl, String> {
    boolean existsByShortenedVersion(String shortenedVersion);
    Optional<ShortenedUrl> findByShortenedVersion(String shortenedVersion);
}
