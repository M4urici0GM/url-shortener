package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.responses.HealthCheckResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping(path = "/api/v1/ping")
public class PingController {

	@GetMapping
	public HealthCheckResponseDto healthCheck() {
		return HealthCheckResponseDto
				.builder()
				.message("Im alive and well!")
				.build();
	}
}
