package dev.mgbarbosa.urlshortner.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "shortened_urls")
public class ShortenedUrl implements Serializable {

    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    private String originalVersion;
    private String shortenedVersion;
    private boolean isPublic;
    private String userId;
    private LocalDateTime createdAt;

    private ShortenedUrl() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortenedUrl(String original, String shortedVersion, String userId, boolean isPublic) {
        this();
        this.userId = userId;
        this.originalVersion = original;
        this.shortenedVersion = shortedVersion;
        this.isPublic = isPublic;
    }
}
