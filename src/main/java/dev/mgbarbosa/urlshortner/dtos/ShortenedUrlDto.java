package dev.mgbarbosa.urlshortner.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenedUrlDto {
    @Getter
    private UUID id;

    @Getter
    private LocalDateTime createdAt;

    @NotEmpty
    @URL
    @Getter
    @Setter
    private String originalUrl;

    @Getter
    @Setter
    private String shortenedUrl;

    public ShortenedUrlDto(ShortenedUrl entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.originalUrl = entity.getOriginalVersion();
        this.shortenedUrl = entity.getShortenedVersion();
    }
}
