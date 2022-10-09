package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/api/v1/shortener")
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
    public ResponseEntity<ShortenedUrlDto> findUrl(@PathParam("shortVersion") String urlId) {
        var result = shortenerService.findShortUrlBy(urlId);
        return ResponseEntity.ok().body(result);
    }
}
