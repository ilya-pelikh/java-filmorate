package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage storage;

    public Collection<Film> getAllFilms() {
        return storage.getAll();
    }

    public Film getFilmById(long id) throws NotFoundException {
        Film foundFilm = storage.getById(id);
        if (foundFilm == null) {
            throw new NotFoundException(String.format("Фильма с id=%s не сущесуствует", id));
        }
        return foundFilm;
    }

    public Film addFilm(Film film) {
        return storage.add(film);
    }

    public Film editFilm(long id, Film film) throws ValidationException {
        if (storage.getById(id) == null) {
            throw new NotFoundException(String.format("Фильма с id=%s не сущесуствует", id));
        }

        return storage.edit(id, film);
    }
}
