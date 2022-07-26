package pl.nqriver.interview.restapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class ApiErrorResponse {

    private final LocalDateTime timestamp;

    private final HttpStatus status;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> errors;


    public static ApiErrorResponse withMapOfErrors(final HttpStatus status,
                                                   final String message,
                                                   final Map<String, String> errors) {
        return new ApiErrorResponse(status, message, errors);
    }

    public static ApiErrorResponse withSimpleMessage(final HttpStatus status, final String message) {
        return new ApiErrorResponse(status, message);
    }


    private ApiErrorResponse(final HttpStatus status, final String message, final Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


    private ApiErrorResponse(final HttpStatus status, final String message) {
        this(status, message, Collections.emptyMap());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}