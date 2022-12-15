package dev.mgbarbosa.urlshortner.entities;

import java.util.UUID;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "users")
public class User implements Serializable {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private final String username;
    private String passwordHash;

    public User(String name, String email, String username, String passwordHash) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
