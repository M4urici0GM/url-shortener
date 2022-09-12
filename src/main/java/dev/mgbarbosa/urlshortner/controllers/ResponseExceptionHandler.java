package dev.mgbarbosa.urlshortner.controllers;

import dev.mgbarbosa.urlshortner.dtos.ApiError;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;
import dev.mgbarbosa.urlshortner.exceptios.InvalidCredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Responsible for handling Validation errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    var fieldError = (FieldError) error;
                    var fieldName = fieldError.getField();
                    var message = fieldError.getDefaultMessage();

                    errors.put(fieldName, message);
                });

        var apiError = new ApiError<>(HttpStatus.BAD_REQUEST, "Validation failed", errors);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExists.class)
    ResponseEntity<Object> entityExistsHandler(EntityExists ex) {
        var apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<Object> invalidCredentials(InvalidCredentialsException ex) {
        var apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    ResponseEntity<Object> genericExceptionHandler(Throwable ex) {
        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
