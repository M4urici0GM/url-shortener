package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


@AutoConfigureDataMongo
@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturn400WithoutName() {
		var userDto = UserDto.builder()
			.email("email@test.com")
			.password("some_pass")
			.username("some_username")
			.name("some test")
			.build();

		var response =  restTemplate.postForEntity("/api/v1/user", userDto, UserDto.class);

		assert response.getStatusCode() == HttpStatusCode.valueOf(201);
	}
}
