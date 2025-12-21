package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;


public class ErrorResponse {
    @Getter
    private final String message;

    protected ErrorResponse(String message) {
        this.message = message;
    }
}