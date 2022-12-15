package dev.mgbarbosa.urlshortner.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mgbarbosa.urlshortner.entities.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticatedUserDetails {
    private String email;
    private String name;
    private String username;
    private UUID id;

    public AuthenticatedUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
