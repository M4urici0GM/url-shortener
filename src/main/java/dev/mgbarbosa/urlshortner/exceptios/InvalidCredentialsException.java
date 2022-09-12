package dev.mgbarbosa.urlshortner.exceptios;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
