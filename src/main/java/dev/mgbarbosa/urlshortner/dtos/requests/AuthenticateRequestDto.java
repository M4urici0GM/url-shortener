package dev.mgbarbosa.urlshortner.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
