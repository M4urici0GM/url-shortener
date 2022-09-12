package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;

public interface ShortenerService {
    ShortenedUrlDto createShortUrl(CreateShortUrlRequest request);
    ShortenedUrlDto findShortUrlBy(String urlId);
}
