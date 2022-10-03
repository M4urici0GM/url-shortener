package dev.mgbarbosa.urlshortner.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "users")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Generated
    private String id;
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
