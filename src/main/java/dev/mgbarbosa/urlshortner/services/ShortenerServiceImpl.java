package dev.mgbarbosa.urlshortner.services;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.exceptios.EntityNotFoundException;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.security.AuthenticationToken;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    private final ShortedUrlRepository shortedUrlRepository;
    private final CachingShortedUrlRepository cachingShortedUrlRepository;

    public ShortenerServiceImpl(
            ShortedUrlRepository shortedUrlRepository,
            CachingShortedUrlRepository cachingShortedUrlRepository) {
        this.shortedUrlRepository = shortedUrlRepository;
        this.cachingShortedUrlRepository = cachingShortedUrlRepository;
    }

    /**
     * Tries to create new shortenedUrl
     * @param request
     * @return
     */
    @Override
    public ShortenedUrlDto createShortUrl(CreateShortUrlRequest request) {
        var maybeClaims = getAuthenticatedUserId();
        var userId = maybeClaims.orElse(new AuthenticatedUserDetails()).getId();
        var randomStr = new AtomicReference<>(generateRandomString(10));

        var retryPolicy = RetryPolicy.builder()
                .handle(RuntimeException.class)
                .withMaxRetries(3)
                .build();

        Failsafe.with(retryPolicy)
                .run(() -> {
                    if (shortedUrlRepository.existsByShortenedVersion(randomStr.get()))
                    {
                        randomStr.set(generateRandomString(10));
                        throw new RuntimeException("RandomStr already exists on the database.");
                    }
                });


        var newShortenedUrl = new ShortenedUrl(request.getUrl(), randomStr.get(), userId, !maybeClaims.isPresent());
        var createdEntity = shortedUrlRepository.save(newShortenedUrl);
        return new ShortenedUrlDto(createdEntity);
    }

    /**
     * Tries to find ShortenedUrl based on specified id.
     *
     * @param urlId the shortenedUrl to be found.
     * @return the found item
     * @throws EntityNotFoundException if entity is not present on the database.
     */
    @Override
    public ShortenedUrlDto findShortUrlBy(String urlId) {
        var maybeCached = cachingShortedUrlRepository.findByShortVersion(urlId);
        if (maybeCached.isPresent())
            return new ShortenedUrlDto(maybeCached.get());

        var maybeShortenedUrl = shortedUrlRepository.findByShortenedVersion(urlId);
        if (!maybeShortenedUrl.isPresent()) {
            throw new EntityNotFoundException("", "");
        }


        var shortenedUrl = maybeShortenedUrl.get();
        cachingShortedUrlRepository.saveByShortVersion(shortenedUrl);

        return new ShortenedUrlDto(shortenedUrl);
    }

    /**
     * Generates Random string containing Uppercase + lowercase characters.
     * @param size the string limit.
     * @return the random string.
     */
    String generateRandomString(int size) {
        var random = new Random();
        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < size; i++) {
            var randomNum = random.nextInt(0, 1000);
            var charNum = (byte) random.nextInt(65, 90);

            if (randomNum > 500) {
                charNum += 32;
            }

            finalString.append((char) charNum);
        }

        return finalString.toString();
    }

    Optional<AuthenticatedUserDetails> getAuthenticatedUserId() {
        var ctx = SecurityContextHolder.getContext();
        if (ctx.getAuthentication() instanceof AuthenticationToken) {
            return Optional.of((AuthenticatedUserDetails) ctx.getAuthentication().getPrincipal());
        }

        return Optional.empty();
    }
}
