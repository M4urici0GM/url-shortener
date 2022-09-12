package dev.mgbarbosa.urlshortner.exceptios;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String key) {
        super(String.format("Entity %s with key %s was not found.", entityName, key));
    }
}
