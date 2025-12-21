package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film getById(long id);

    Film add(Film film);

    Film edit(long id, Film film);

    Collection<Film> getAll();
}
