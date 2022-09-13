package dev.mgbarbosa.urlshortner.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequestDto {
    @NotEmpty(message = "Username is required.")
    @Getter
    @Setter
    private String username;

    @NotEmpty(message = "Password is required.")
    @Getter
    @Setter
    private String password;

}
