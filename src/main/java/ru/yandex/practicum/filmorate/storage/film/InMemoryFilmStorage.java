package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film getById(long id) {
        return films.get(id);
    }

    @Override
    public Film add(Film film) {
        long id = getNextId();
        film.setId(id);

        films.put(id, film);

        return film;
    }

    @Override
    public Film edit(long id, Film film) {
        films.put(id, film);

        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
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
