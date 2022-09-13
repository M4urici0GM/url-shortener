package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.annotations.WithUserMock;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.exceptios.EntityNotFoundException;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@DisplayName("Shortener Service tests")
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ShortenerServiceImplTests {

    @Mock
    ShortedUrlRepository shortedUrlRepository;

    @Mock
    CachingShortedUrlRepository cachingShortedUrlRepository;

    @InjectMocks
    ShortenerServiceImpl shortenerService;

    @Test(expected = RuntimeException.class)
    @WithUserMock()
    @DisplayName("Should throw exception if max retries")
    public void shouldThrowExceptionIfRetryLimitReached() {
        // Arrange
        var request = new CreateShortUrlRequest();

        Mockito.when(shortedUrlRepository.existsByShortenedVersion(ArgumentMatchers.any()))
                .thenReturn(true,true, true);

        // Act
        shortenerService.createShortUrl(request);

    }

    @Test
    @WithUserMock(id = "6320299bf86defddbabc9ea8")
    @DisplayName("Should create url as not public if user is logged-in")
    public void shouldCreatePublicShortenedUrl() {
        // Arrange
        var request = new CreateShortUrlRequest("https://github.com/m4urici0gm");

        Mockito.when(shortedUrlRepository.save(ArgumentMatchers.any(ShortenedUrl.class)))
                .thenAnswer((Answer<ShortenedUrl>) invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        var result = shortenerService.createShortUrl(request);

        // Assert
        assert result.getOriginalUrl() == request.getUrl();

        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .save(ArgumentMatchers.argThat(shortenedUrl -> {
                    return !shortenedUrl.isPublic() && shortenedUrl.getUserId().equals("6320299bf86defddbabc9ea8");
                }));
    }

    @Test
    @DisplayName("Should create url as not public if user is logged-in")
    public void shouldCreatePrivateShortenedUrl() {
        // Arrange
        var request = new CreateShortUrlRequest("https://github.com/m4urici0gm");

        Mockito.when(shortedUrlRepository.save(ArgumentMatchers.any(ShortenedUrl.class)))
                .thenAnswer((Answer<ShortenedUrl>) invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        var result = shortenerService.createShortUrl(request);

        // Assert
        assert result.getOriginalUrl() == request.getUrl();

        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .save(ArgumentMatchers.argThat(ShortenedUrl::isPublic));
    }

    @Test
    @WithUserMock()
    @DisplayName("Should return from cache")
    public void shouldReturnFromCacheCorrectly() {
        // Arrange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, "632020a9d379c96722cefee3", false);

        Mockito.when(cachingShortedUrlRepository.findByShortVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.of(expectedObj));

        // Act
        var result = shortenerService.findShortUrlBy(randomId);

        // Assert
        Mockito.verify(shortedUrlRepository, Mockito.times(0))
                .findByShortenedVersion(ArgumentMatchers.any());
    }

    @Test(expected = EntityNotFoundException.class)
    @WithUserMock()
    @DisplayName("Should return from cache")
    public void shouldThrowIfShortenedUrlNotFound() {
        // Arrange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, "632020a9d379c96722cefee3", false);

        Mockito.when(cachingShortedUrlRepository.findByShortVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.empty());

        // Act
        shortenerService.findShortUrlBy(randomId);
    }

    @Test
    @WithUserMock()
    @DisplayName("Should go to database if not in cache.")
    public void shouldCallDatabaseIfCacheNotPresent() {
        // Arange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, "632020a9d379c96722cefee3", false);

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
