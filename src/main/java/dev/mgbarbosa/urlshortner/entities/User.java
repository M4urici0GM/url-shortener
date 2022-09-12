package dev.mgbarbosa.urlshortner.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "users")
public class User implements Serializable {

    @Id
    @Generated
    @Getter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private final String username;

    @Getter
    @Setter
    private String passwordHash;

    public User(String name, String email, String username, String passwordHash) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
