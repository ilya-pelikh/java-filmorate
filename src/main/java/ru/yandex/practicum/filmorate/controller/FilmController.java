package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> get() {
        return films.values();
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        long id = getNextId();
        film.setId(id);
        log.trace("Добавление фильма{}->{}", films.get(id), film);
        films.put(id, film);

        return film;
    }

    @PutMapping
    public Film update(@Validated(ValidationGroups.OnUpdate.class) @RequestBody Film film) {
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
