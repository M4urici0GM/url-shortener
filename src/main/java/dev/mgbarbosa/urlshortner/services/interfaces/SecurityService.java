package dev.mgbarbosa.urlshortner.services.interfaces;

public interface SecurityService {
    String hashPassword(String input);
    boolean verifyPassword(String password, String hash);
}
