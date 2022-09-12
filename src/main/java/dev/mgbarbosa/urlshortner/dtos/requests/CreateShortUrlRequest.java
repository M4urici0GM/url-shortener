package dev.mgbarbosa.urlshortner.dtos.requests;

import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public class CreateShortUrlRequest {

    @NotEmpty(message = "url is required for shortening.")
    @URL(message = "url should be a valid url.")
    private String url;
}
