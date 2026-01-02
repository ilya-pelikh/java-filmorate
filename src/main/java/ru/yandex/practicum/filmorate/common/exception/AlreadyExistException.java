package ru.yandex.practicum.filmorate.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistException extends ResponseStatusException {
    public AlreadyExistException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
