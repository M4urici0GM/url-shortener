package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.ShortenedUrlDto;
import dev.mgbarbosa.urlshortner.dtos.requests.CreateShortUrlRequest;
import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/shortener")
public class ShortenerController {
    private final ShortenerService shortenerService;

    @Autowired
    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping
    @ResponseBody
    public ShortenedUrlDto shortUrl(@Valid @RequestBody CreateShortUrlRequest request) {
        return shortenerService.createShortUrl(request);
    }

    @GetMapping("/public")
    public String testPublic() {
        return "Hello World";
    }
}
