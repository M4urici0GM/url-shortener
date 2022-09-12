package dev.mgbarbosa.urlshortner.dtos;

import dev.mgbarbosa.urlshortner.entities.ShortenedUrl;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenedUrlDto {
    @Getter
    private String id;

    @Getter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @NotEmpty
    @URL
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
