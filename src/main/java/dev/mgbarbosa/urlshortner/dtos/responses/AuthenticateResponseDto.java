package dev.mgbarbosa.urlshortner.dtos.responses;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticateResponseDto {
    private UserDto userDetails;
    private JwtToken token;
    private JwtToken refreshToken;
}

