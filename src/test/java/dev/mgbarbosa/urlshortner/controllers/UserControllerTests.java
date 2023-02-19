package dev.mgbarbosa.urlshortner.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.mgbarbosa.urlshortner.dtos.ApiError;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import java.util.HashMap;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.annotation.DirtiesContext;


@AutoConfigureDataMongo
@DirtiesContext
@SuppressWarnings("unchecked")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;

	@Test
	@DisplayName("should create a user correctly")
	public void shouldCreateUserCorrectly() {
		// Arrange
		final var userDto = UserDto.builder()
				.name("Mourice Barbosa")
				.email("mourice@mgbarbosa.dev")
				.password("blueScreen#666")
				.username("m4urici0gm")
				.build();

        when(userRepository.save(any())).thenAnswer((Answer<User>) (invocation) -> invocation.getArgument(0));

		// Act
		final var response = restTemplate.postForEntity("/api/v1/user", userDto, UserDto.class);

		// Assert
		assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
	}

	@Test
	@DisplayName("should return 400 when name is not present.")
	public void shouldReturn400WithoutName() {
		var userDto = UserDto.builder()
			.email("email@test.com")
			.password("some_pass")
			.username("some_username")
			.build();

		// used only for getting the class type on runtime.
		final var testObject = new ApiError<HashMap<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		final var response = restTemplate.postForEntity("/api/v1/user", userDto, testObject.getClass());

		final var responseBody = Objects.requireNonNull(response.getBody());
		final var errors = (HashMap<String, String>) responseBody.getErrors();

		assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
		assertEquals("Validation failed", responseBody.getMessage());
		assertEquals("must not be empty", errors.get("name"));
	}

	@Test
	@DisplayName("should return 400 when email is not present.")
	public void shouldReturn400_whenEmailIsMissing() {
		var userDto = UserDto.builder()
				.name("some name")
				.password("some_pass")
				.username("some_username")
				.build();

		// used only for getting the class type on runtime.
		final var testObject = new ApiError<HashMap<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		final var response = restTemplate.postForEntity("/api/v1/user", userDto, testObject.getClass());

		final var responseBody = Objects.requireNonNull(response.getBody());
		final var errors = (HashMap<String, String>) responseBody.getErrors();

		assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
		assertEquals("Validation failed", responseBody.getMessage());
		assertEquals("Email address is required.", errors.get("email"));
	}

	@Test
	@DisplayName("should return 400 when password is not present.")
	public void shouldReturn400_whenPasswordIsInvalid() {
		var userDto = UserDto.builder()
				.name("some name")
				.password("1234")
				.email("some_email@someprovider.com")
				.username("some_username")
				.build();

		// used only for getting the class type on runtime.
		final var testObject = new ApiError<HashMap<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		final var response = restTemplate.postForEntity("/api/v1/user", userDto, testObject.getClass());

		final var responseBody = Objects.requireNonNull(response.getBody());
		final var errors = (HashMap<String, String>) responseBody.getErrors();

		assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
		assertEquals("Validation failed", responseBody.getMessage());
		assertEquals("Password requires at least 5 chars.", errors.get("password"));
	}
}
