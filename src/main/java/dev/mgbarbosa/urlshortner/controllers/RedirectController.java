package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.services.interfaces.ShortenerService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/redirect")
public class RedirectController {

    private final ShortenerService shortenerService;

    public RedirectController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @GetMapping("/{shortVersion}")
    public void redirect(@PathVariable String shortVersion, HttpServletResponse httpResponse) throws IOException {
        var result = shortenerService.findShortUrlBy(shortVersion);
        httpResponse.sendRedirect(result.getOriginalUrl());
    }
}
