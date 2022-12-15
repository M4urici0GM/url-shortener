package dev.mgbarbosa.urlshortner.services;

import com.github.javafaker.Faker;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.AuthenticationService;
import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;

@DisplayName("Shortener Service tests")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class ShortenerServiceImplTests {

    @Mock
    ShortedUrlRepository shortedUrlRepository;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    CachingShortedUrlRepository cachingShortedUrlRepository;

    @InjectMocks
    ShortenerServiceImpl shortenerService;

    @Test
    @DisplayName("Should throw exception if max retries")
    public void shouldThrowExceptionIfRetryLimitReached() throws AccessDeniedException {
        // Arrange
        var request = new CreateShortUrlRequest();

        Mockito.when(shortedUrlRepository.existsByShortenedVersion(ArgumentMatchers.any()))
                .thenReturn(true,true, true);

        // Act
        shortenerService.createShortUrl(request);

    }

    @Test
    @DisplayName("Should create url as public if user is not logged-in")
    public void shouldCreatePublicShortenedUrl() throws AccessDeniedException {
        // Arrange

        var request = new CreateShortUrlRequest("https://github.com/m4urici0gm");

        Mockito.when(authenticationService.isAuthenticated()).thenReturn(false);

        Mockito.when(shortedUrlRepository.save(ArgumentMatchers.any(ShortenedUrl.class)))
                .thenAnswer((Answer<ShortenedUrl>) invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        var result = shortenerService.createShortUrl(request);

        // Assert
        assert Objects.equals(result.getOriginalUrl(), request.getUrl());

        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .save(ArgumentMatchers.argThat(ShortenedUrl::isPublic));

    }

    @Test
    @DisplayName("Should create url as not public if user is logged-in")
    public void shouldCreatePrivateShortenedUrl() throws AccessDeniedException {
        // Arrange
        var request = new CreateShortUrlRequest("https://github.com/m4urici0gm");
        var faker = new Faker();
        var userId = UUID.randomUUID();
        var authenticatedUser = new AuthenticatedUserDetails(
                faker.internet().emailAddress(),
                faker.name().fullName(),
                "some.username",
                userId);

        Mockito.when(authenticationService.isAuthenticated()).thenReturn(true);

        Mockito.when(authenticationService.getAuthenticatedUser()).thenReturn(authenticatedUser);

        Mockito.when(shortedUrlRepository.save(ArgumentMatchers.any(ShortenedUrl.class)))
                .thenAnswer((Answer<ShortenedUrl>) invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        var result = shortenerService.createShortUrl(request);

        // Assert
        assert Objects.equals(result.getOriginalUrl(), request.getUrl());

        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .save(ArgumentMatchers.argThat(shortenedUrl -> {
                    return !shortenedUrl.isPublic() && shortenedUrl.getUserId().equals("6320299bf86defddbabc9ea8");
                }));
    }

    @Test
    @DisplayName("Should return from cache")
    public void shouldReturnFromCacheCorrectly() {
        // Arrange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, UUID.randomUUID(), false);

        Mockito.when(cachingShortedUrlRepository.findByShortVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.of(expectedObj));

        // Act
        var result = shortenerService.findShortUrlBy(randomId);

        // Assert
        Mockito.verify(shortedUrlRepository, Mockito.times(0))
                .findByShortenedVersion(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Should return from cache")
    public void shouldThrowIfShortenedUrlNotFound() {
        // Arrange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, UUID.randomUUID(), false);

        Mockito.when(cachingShortedUrlRepository.findByShortVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.empty());

        // Act
        shortenerService.findShortUrlBy(randomId);
    }

    @Test
    @DisplayName("Should go to database if not in cache.")
    public void shouldCallDatabaseIfCacheNotPresent() {
        // Arange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, UUID.randomUUID(), false);

        Mockito.when(shortedUrlRepository.findByShortenedVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.of(expectedObj));

        // Act
        var result = shortenerService.findShortUrlBy(randomId);

        // Assert
        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .findByShortenedVersion(ArgumentMatchers.any());

        Mockito.verify(cachingShortedUrlRepository, Mockito.times(1))
                .findByShortVersion(ArgumentMatchers.any());
    }
}
