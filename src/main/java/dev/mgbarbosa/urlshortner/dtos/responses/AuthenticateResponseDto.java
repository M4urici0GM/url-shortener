package dev.mgbarbosa.urlshortner.dtos.responses;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponseDto {
    @Getter
    @Setter
    private UserDto userDetails;

    @Getter
    @Setter
    private String token;
}
