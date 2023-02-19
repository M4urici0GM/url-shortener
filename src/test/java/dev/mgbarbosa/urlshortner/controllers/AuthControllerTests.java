package dev.mgbarbosa.urlshortner.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private UserRepository userRepository;

  @Test
  @DirtiesContext
  @DisplayName("should be able to make login correctly")
  public void shouldBeAbleToMakeLoginCorrectly() {
    // Arrange
    final var userDto = User.builder()
        .username("m4urici0gm")
        .passwordHash(BCrypt.hashpw("blueScreen#666", BCrypt.gensalt()))
        .email("contact@mgbarbosa.dev")
        .name("Mourice")
        .build();

    final var loginDto = AuthenticateRequestDto.builder()
        .username(userDto.getUsername())
        .password("blueScreen#666")
        .grantType("password")
        .build();

    when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(userDto));

    // Act
    final var response = restTemplate.postForEntity("/v1/auth", loginDto, AuthenticateResponseDto.class);

    // Assert
    final var responseBody = Objects.requireNonNull(response.getBody());

    assertEquals(HttpStatus.valueOf(201), response.getStatusCode());
    assertTrue(Objects.nonNull(responseBody.getToken()));
    assertTrue(Objects.nonNull(responseBody.getRefreshToken()));
  }

  @Test
  @DirtiesContext
  @DisplayName("should be able to get current user profile.")
  public void shouldBeAbleToGetCurrentUserProfile() {
    // Arrange
    final var password = "blueScreen#666";
    final var userDto = User.builder()
        .username("m4urici0gm")
        .passwordHash(BCrypt.hashpw(password, BCrypt.gensalt()))
        .email("contact@mgbarbosa.dev")
        .name("Mourice")
        .build();

    final var loginDto = AuthenticateRequestDto.builder()
        .username(userDto.getUsername())
        .password(password)
        .grantType("password")
        .build();

    when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(userDto));

    // Act
    final var loginResponse = restTemplate.postForEntity("/v1/auth", loginDto, AuthenticateResponseDto.class);
    final var responseBody = Objects.requireNonNull(loginResponse.getBody());

    final var baseUrl = restTemplate.getRootUri() + "/v1/auth/profile";
    final var headers = new HttpHeaders();
    headers.set("Authorization", String.format("Bearer %s", responseBody.getToken().getToken()));

    final var response = restTemplate.exchange(
        baseUrl,
        HttpMethod.GET,
        new HttpEntity<AuthenticatedUserDetails>(headers),
        AuthenticatedUserDetails.class);


    // Assert

    assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
  }

  @Test
  @DirtiesContext
  @DisplayName("should 401 when trying to get profile and not authenticated")
  public void shouldReturn401WhenTryingToGetProfileButNotAuthenticated() {
    // Act
    final var response = restTemplate.getForEntity("/v1/auth/profile", AuthenticatedUserDetails.class);
    // Assert

    assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
  }
}
