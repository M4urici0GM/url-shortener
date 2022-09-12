package dev.mgbarbosa.urlshortner.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "shortened_urls")
public class ShortenedUrl implements Serializable {

    @Id
    @Generated
    @Getter
    private String id;

    @Getter
    @Setter
    private String originalVersion;

    @Getter
    @Setter
    private String shortenedVersion;

    @Getter
    private String userId;

    @Getter
    private final LocalDateTime createdAt;

    private ShortenedUrl() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortenedUrl(String userId, String original, String shortedVersion) {
        this();
        this.userId = userId;
        this.originalVersion = original;
        this.shortenedVersion = shortedVersion;
    }
}
