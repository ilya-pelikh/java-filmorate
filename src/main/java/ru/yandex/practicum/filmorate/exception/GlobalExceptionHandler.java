package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<ErrorResponse> handleAllResponseStatusExceptions(ResponseStatusException exception) {
        ErrorResponse response = new ErrorResponse(exception.getReason());
        HttpStatusCode status = exception.getStatusCode();
        return new ResponseEntity<>(response,status);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleAllRuntimeExceptions() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}