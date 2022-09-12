package dev.mgbarbosa.urlshortner.exceptios;

public class EntityExists extends RuntimeException {
    public EntityExists(String entityName, String key) {
        super(String.format("Entity %s with key %s already exists.", entityName, key));
    }
}
