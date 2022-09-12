package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import org.springframework.stereotype.Service;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    @Override
    public ShortenedUrlDto createShortUrl(CreateShortUrlRequest request) {
        return null;
    }
}
