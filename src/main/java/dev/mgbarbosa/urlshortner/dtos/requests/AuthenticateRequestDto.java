package dev.mgbarbosa.urlshortner.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticateRequestDto {
    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "GrantType is required.")
    @Pattern(
            regexp = "password|refreshToken",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "GrantType should be one of 'password' or 'refreshToken'")
    private String grantType;
}
