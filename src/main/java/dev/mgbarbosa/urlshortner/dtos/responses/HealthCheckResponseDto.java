package dev.mgbarbosa.urlshortner.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class HealthCheckResponseDto {
	private final HttpStatus status;
}
