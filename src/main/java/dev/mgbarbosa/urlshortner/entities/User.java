package dev.mgbarbosa.urlshortner.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String username;
    private String passwordHash;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String name, String email, String username, String passwordHash) {
        this();
        this.name = name;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
