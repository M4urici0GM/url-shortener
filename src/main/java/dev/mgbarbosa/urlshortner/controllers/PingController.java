package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.responses.HealthCheckResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ping")
public class PingController {

	@GetMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<HealthCheckResponseDto> healthCheck() {
		return ResponseEntity.ok()
			.body(HealthCheckResponseDto
				.builder()
				.status(HttpStatus.OK)
				.build());
	}
}
