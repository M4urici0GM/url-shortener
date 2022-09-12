package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserDto {
    @Getter
    @Setter
    private UserDto userDetails;

    @Getter
    @Setter
    private String token;
}
