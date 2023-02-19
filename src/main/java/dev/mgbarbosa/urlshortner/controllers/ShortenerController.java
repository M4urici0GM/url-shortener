package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/shortener")
@PreAuthorize("permitAll()")
public class ShortenerController {
    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ShortenedUrlDto> shortUrl(@Valid @RequestBody CreateShortUrlRequest request)
            throws
            AccessDeniedException,
            URISyntaxException {

        return ResponseEntity
                .created(new URI(""))
                .body(shortenerService.createShortUrl(request));
    }

    @GetMapping("/{shortVersion}")
    public ResponseEntity<ShortenedUrlDto> findUrl(@PathVariable String shortVersion) {
        var result = shortenerService.findShortUrlBy(shortVersion);
        return ResponseEntity.ok().body(result);
    }
}
