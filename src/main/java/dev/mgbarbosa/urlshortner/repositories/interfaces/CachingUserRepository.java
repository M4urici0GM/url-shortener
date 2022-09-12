package dev.mgbarbosa.urlshortner.repositories.interfaces;

import dev.mgbarbosa.urlshortner.entities.User;

import java.util.Optional;

public interface CachingUserRepository {
    Optional<User> findById(String id);
}
