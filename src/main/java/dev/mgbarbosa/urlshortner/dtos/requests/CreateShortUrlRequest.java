package dev.mgbarbosa.urlshortner.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
public class CreateShortUrlRequest {

    @Getter
    @Setter
    @NotEmpty(message = "url is required for shortening.")
    @URL(message = "url should be a valid url.")
    private String url;
}
