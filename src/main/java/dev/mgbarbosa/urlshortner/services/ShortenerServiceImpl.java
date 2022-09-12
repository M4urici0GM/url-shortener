package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.exceptios.EntityNotFoundException;
import dev.mgbarbosa.urlshortner.repositories.ShortedUrlRepository;
import dev.mgbarbosa.urlshortner.security.UserClaims;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    private final ShortedUrlRepository shortedUrlRepository;

    public ShortenerServiceImpl(ShortedUrlRepository shortedUrlRepository) {
        this.shortedUrlRepository = shortedUrlRepository;
    }

    @Override
    public ShortenedUrlDto createShortUrl(CreateShortUrlRequest request) {
        var maybeClaims = getAuthenticatedUserId();
        var userId = maybeClaims.orElse(new UserClaims()).getId();
        var randomStr = generateRandomString(10);

        while (shortedUrlRepository.existsByShortenedVersion(randomStr)) {
            randomStr = generateRandomString(10);
        }

        var newShortenedUrl = new ShortenedUrl(request.getUrl(), randomStr, userId, !maybeClaims.isPresent());
        var createdEntity = shortedUrlRepository.save(newShortenedUrl);
        return new ShortenedUrlDto(createdEntity);
    }

    @Override
    public ShortenedUrlDto findShortUrlBy(String urlId) {
        var maybeShortenedUrl = shortedUrlRepository.findByShortenedVersion(urlId);
        if (!maybeShortenedUrl.isPresent()) {
            throw new EntityNotFoundException("", "");
        }

        var shortenedUrl = maybeShortenedUrl.get();
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

    Optional<UserClaims> getAuthenticatedUserId() {
        var ctx = SecurityContextHolder.getContext();
        if (ctx.getAuthentication() instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (ctx.getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            return Optional.of((UserClaims) ctx.getAuthentication().getCredentials());
        }

        return Optional.empty();
    }
}
