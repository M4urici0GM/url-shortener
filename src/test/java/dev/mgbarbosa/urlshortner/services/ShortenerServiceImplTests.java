package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.annotations.WithUserMock;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.repositories.interfaces.CachingShortedUrlRepository;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
                .findByShortVersion(ArgumentMatchers.any());
    }

    @Test
    @WithUserMock()
    @DisplayName("Should go to database if not in cache.")
    public void shouldCallDatabaseIfCacheNotPresent() {
        // Arange
        var randomId = "abCd";
        var expectedObj = new ShortenedUrl("https://google.com", randomId, "632020a9d379c96722cefee3", false);

        Mockito.when(shortedUrlRepository.findByShortVersion(ArgumentMatchers.eq(randomId)))
                .thenReturn(Optional.of(expectedObj));

        // Act
        var result = shortenerService.findShortUrlBy(randomId);

        // Assert
        Mockito.verify(shortedUrlRepository, Mockito.times(1))
                .findByShortVersion(ArgumentMatchers.any());

        Mockito.verify(cachingShortedUrlRepository, Mockito.times(0))
                .findByShortVersion(ArgumentMatchers.any());
    }


//    @Test(expected = RuntimeException.class)
//    @WithUserMock(id = "632020a9d379c96722cefee3")
//    @DisplayName("Should return from cache")
//    public void shouldReturnFromCacheCorrectly2() {
//        // Arrange
//        var request = new CreateShortUrlRequest("https://google.com");
//
//        Mockito.when(shortedUrlRepository.save(ArgumentMatchers.any(ShortenedUrl.class)))
//                .thenAnswer(new Answer<ShortenedUrl>() {
//                    @Override
//                    public ShortenedUrl answer(InvocationOnMock invocationOnMock) throws Throwable {
//                        return invocationOnMock.getArgument(0);
//                    }
//                });
//
//        // Act
//        var result = shortenerService.createShortUrl(request);
//    }
}
