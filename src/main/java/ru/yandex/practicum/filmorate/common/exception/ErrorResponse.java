package ru.yandex.practicum.filmorate.common.exception;

import lombok.Getter;

public class ErrorResponse {
    @Getter
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}