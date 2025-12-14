package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static final LocalDate START_OF_FILM_MAKING = LocalDate.of(1895, Month.DECEMBER, 28);
    private final Map<Long, Film> films = new HashMap<>();

    public static void validate(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            throw new ValidationException("название не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("максимальная длина описания — 200 символов");
        }

        if (film.getReleaseDate().isBefore(START_OF_FILM_MAKING)) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }
    }

    @GetMapping
    public Collection<Film> get() {
        return films.values();
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        try {
            validate(film);
        } catch (ValidationException exception) {
            log.info("{}{}", exception.getMessage(), film);
            throw exception;
        }

        long id = getNextId();
        film.setId(id);
        log.trace("Добавление фильма{}->{}", films.get(id), film);
        films.put(id, film);

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        try {
            validate(film);
        } catch (ValidationException exception) {
            log.info("{}{}", exception.getMessage(), film);
            throw exception;
        }


        long id = film.getId();

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Пользователя с данный id не сущесуствует");
        }


        log.trace("Обновление фильма{}->{}", films.get(id), film);
        films.put(id, film);

        return film;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
