package dev.mgbarbosa.urlshortner.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError<T> {
    @Getter
    private final LocalDateTime timestamp;

    @Getter
    private HttpStatus httpStatus;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String debugMessage;

    @Getter
    @Setter
    private T errors;

    private ApiError() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public ApiError(HttpStatus httpStatus, String message, T errors) {
        this(httpStatus);
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable throwable) {
        this(httpStatus);
        this.message = message;
        this.debugMessage = throwable.getLocalizedMessage();
    }
}
