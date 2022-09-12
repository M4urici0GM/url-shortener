package dev.mgbarbosa.urlshortner.repositories;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SortedUrlRepository extends PagingAndSortingRepository<ShortenedUrl, String> {

}
