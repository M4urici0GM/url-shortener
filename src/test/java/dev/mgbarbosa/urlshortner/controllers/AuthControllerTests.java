package dev.mgbarbosa.urlshortner.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.AuthenticateRequestDto;
import dev.mgbarbosa.urlshortner.dtos.responses.AuthenticateResponseDto;
import dev.mgbarbosa.urlshortner.security.AuthenticatedUserDetails;
import dev.mgbarbosa.urlshortner.services.interfaces.UserService;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
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

  @Autowired
  private UserService userService;

  @Test
  @DirtiesContext
  @DisplayName("should be able to make login correctly")
  public void shouldBeAbleToMakeLoginCorrectly() {
    // Arrange
    final var userDto = UserDto.builder()
        .username("m4urici0gm")
        .password("blueScreen#666")
        .email("contact@mgbarbosa.dev")
        .name("Mourice")
        .build();

    final var loginDto = AuthenticateRequestDto.builder()
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .grantType("password")
        .build();

    userService.createUser(userDto);

    // Act
    final var response = restTemplate.postForEntity("/api/v1/auth", loginDto, AuthenticateResponseDto.class);

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
    final var userDto = UserDto.builder()
        .username("m4urici0gm")
        .password("blueScreen#666")
        .email("contact@mgbarbosa.dev")
        .name("Mourice")
        .build();

    final var loginDto = AuthenticateRequestDto.builder()
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .grantType("password")
        .build();

    userService.createUser(userDto);

    // Act
    final var loginResponse = restTemplate.postForEntity("/api/v1/auth", loginDto, AuthenticateResponseDto.class);
    final var responseBody = Objects.requireNonNull(loginResponse.getBody());


    final var baseUrl = restTemplate.getRootUri() + "/api/v1/auth/profile";
    final var headers = new HttpHeaders();
    headers.set("Authorization", String.format("Bearer %s", responseBody.getToken().getToken()));

    final var requestEntity = new HttpEntity<AuthenticatedUserDetails>(headers);
    final var response = restTemplate.exchange(
        baseUrl,
        HttpMethod.GET,
        requestEntity,
        AuthenticatedUserDetails.class);


    // Assert

    assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
  }

  @Test
  @DirtiesContext
  @DisplayName("should 401 when trying to get profile and not authenticated")
  public void shouldReturn401WhenTryingToGetProfileButNotAuthenticated() {
    // Arrange
    final var userDto = UserDto.builder()
        .username("m4urici0gm")
        .password("blueScreen#666")
        .email("contact@mgbarbosa.dev")
        .name("Mourice")
        .build();

    userService.createUser(userDto);

    // Act
    final var response = restTemplate.getForEntity("/api/v1/auth/profile", AuthenticatedUserDetails.class);
    // Assert

    assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
  }
}
