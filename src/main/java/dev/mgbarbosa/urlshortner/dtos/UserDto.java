package dev.mgbarbosa.urlshortner.dtos;

import dev.mgbarbosa.urlshortner.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private UUID id;

    @NotEmpty
    private String name;

    @Email
    @NotEmpty(message = "Email address is required.")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password requires at least 5 chars.")
    private String password;

    @NotEmpty(message = "username is required.")
    @Size(min = 5, message = "username need to have at least 5 chars.")
    private String username;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
