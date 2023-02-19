package dev.mgbarbosa.urlshortner.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import dev.mgbarbosa.urlshortner.repositories.interfaces.ShortedUrlRepository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedirectControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private ShortedUrlRepository repository;

  @Test
  @DisplayName("should redirect correctly")
  public void shouldRedirectCorrectly_whenUrlExists() throws IOException, InterruptedException {
    // Arrange
    final var expectedRedirectUri = "http://google.com";
    final var expectedShortenedUrl = ShortenedUrl.builder()
        .shortenedVersion("some_short")
        .isPublic(true)
        .id(UUID.randomUUID())
        .originalVersion(expectedRedirectUri)
        .build();

    when(repository.findByShortenedVersion(expectedShortenedUrl.getShortenedVersion()))
        .thenReturn(Optional.of(expectedShortenedUrl));

    final var httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();
    final var baseUri = restTemplate.getRootUri();
    final var uri = URI.create(String.format("%s/api/v1/redirect/some_short", baseUri));
    final var httpRequest = HttpRequest.newBuilder()
        .uri(uri)
        .GET()
        .build();

    // Act
    final var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    final var locationHeader = response.headers().firstValue("location").orElseThrow();

    // Assert
    assertEquals(302, response.statusCode());
    assertEquals(expectedRedirectUri, locationHeader);
  }
}
