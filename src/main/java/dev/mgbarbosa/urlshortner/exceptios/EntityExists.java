package dev.mgbarbosa.urlshortner.exceptios;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityExists extends RuntimeException {
    public EntityExists(String entityName, String key) {
        super(String.format("Entity %s with key %s already exists.", entityName, key));
    }
}
