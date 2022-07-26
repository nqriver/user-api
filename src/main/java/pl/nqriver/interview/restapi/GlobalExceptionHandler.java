package pl.nqriver.interview.restapi;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.nqriver.interview.restapi.user.InvalidUsernameOrPasswordException;
import pl.nqriver.interview.restapi.user.UserAlreadyExistsException;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(InvalidUsernameOrPasswordException exception) {
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.UNAUTHORIZED,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.CONFLICT,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFound(UsernameNotFoundException exception) {
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException exception,
                                                                              WebRequest request) {
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.FORBIDDEN,
                request.getContextPath() + " " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception) {
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.METHOD_NOT_ALLOWED,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        final Map<String, String> errorMap = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(),
                        objectError -> Objects.isNull(objectError.getDefaultMessage()) ? "" : objectError.getDefaultMessage()));
        final String message = "Argument validation failed";
        final ApiErrorResponse errorResponse =
                ApiErrorResponse.withMapOfErrors(HttpStatus.BAD_REQUEST, message, errorMap);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataViolations(DataIntegrityViolationException exception) {
        String rootMsg = Objects.requireNonNull(exception.getRootCause()).getMessage();
        final ApiErrorResponse errorResponse = ApiErrorResponse.withSimpleMessage(HttpStatus.CONFLICT, rootMsg);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAnyException(Exception exception) {
        final ApiErrorResponse errorResponse = ApiErrorResponse
                .withSimpleMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
