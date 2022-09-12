package dev.mgbarbosa.urlshortner.dtos;

import dev.mgbarbosa.urlshortner.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    @Getter
    @Setter
    String id;

    @NotEmpty
    @Getter
    @Setter
    String name;

    @Email
    @Getter
    @Setter
    @NotEmpty(message = "Email address is required.")
    String email;

    @Getter
    @Setter
    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password requires at least 5 chars.")
    String password;

    @Getter
    @Setter
    @NotEmpty(message = "username is required.")
    @Size(min = 5, message = "username need to have at least 5 chars.")
    String username;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
