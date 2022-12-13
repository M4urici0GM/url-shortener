package dev.mgbarbosa.urlshortner.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateShortUrlRequest {
    @NotEmpty(message = "url is required for shortening.")
    @URL(message = "url should be a valid url.")
    private String url;
}
