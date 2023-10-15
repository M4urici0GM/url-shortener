package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import jakarta.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/shortener")
@PreAuthorize("permitAll()")
public class ShortenerController {
    private final ShortenerService shortenerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortenedUrlDto shortUrl(@Valid @RequestBody CreateShortUrlRequest request) throws AccessDeniedException {
        return shortenerService.createShortUrl(request);
    }

    @GetMapping("/{shortVersion}")
    public ShortenedUrlDto findUrl(@PathVariable String shortVersion) {
        return shortenerService.findShortUrlBy(shortVersion);
    }
}
