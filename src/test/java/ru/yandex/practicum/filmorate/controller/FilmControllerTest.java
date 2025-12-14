package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Фильм про бориса");
        film.setDescription("Фильм о путешествиях через червоточину");
        film.setReleaseDate(LocalDate.of(2014, 10, 26));
        film.setDuration(169);
    }

    @Test
    void validate_film_ShouldNotThrowException() {
        assertDoesNotThrow(() -> FilmController.validate(film));
    }

    @Test
    void validate_ShouldThrowIfNameIsBlank() {
        film.setName("");

        assertThrows(ValidationException.class, () -> FilmController.validate(film));
    }

    @Test
    void validate_ShouldThrowIfDescriptionLengthIsMoreThan200() {
        film.setDescription("s".repeat(201));

        assertThrows(ValidationException.class, () -> FilmController.validate(film));
    }

    @Test
    void validate_ShouldThrowIfReleaseDateIsEarlierThanFirstFilmMakingDay() {
        film.setReleaseDate(LocalDate.of(1894, 12, 28));

        assertThrows(ValidationException.class, () -> FilmController.validate(film));
    }

    @Test
    void validate_ShouldThrowIfDurationIsNotPositive() {
        film.setDuration(0);

        assertThrows(ValidationException.class, () -> FilmController.validate(film));
    }
}
