package dev.mgbarbosa.urlshortner.dtos.responses;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticateResponseDto {
    private UserDto userDetails;
    private JwtToken token;
    private JwtToken refreshToken;
}

