package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeStorage likeStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Like addLikeToFilmByUser(long userId, long filmId) {
        User user = userStorage.getById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        Film film = filmStorage.getById(filmId);

        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", filmId));
        }

        Like like = new Like();
        like.setUser(user);
        like.setFilm(film);

        if (likeStorage.contains(like)) {
            throw new AlreadyExistException("Лайк уже был поставлен");
        }

        return likeStorage.add(like);
    }

    public Like removeLikeFromFilmByUser(long userId, long filmId) {
        User user = userStorage.getById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        Film film = filmStorage.getById(filmId);

        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", filmId));
        }

        Like like = new Like();
        like.setUser(user);
        like.setFilm(film);

        if (!likeStorage.contains(like)) {
            throw new AlreadyExistException("Лайк еще не был поставлен");
        }

        return likeStorage.remove(like);
    }

    public Collection<Film> getFilmsByPopularity(Integer count) {
        Map<Film, Long> mapOfPopularity = new HashMap<>();

        likeStorage.getAll().forEach(like -> {
            mapOfPopularity.compute(like.getFilm(), (key, value) -> value == null ? 1 : value + 1);
        });

        List<Map.Entry<Film, Long>> entries =
                new ArrayList<>(mapOfPopularity.entrySet());

        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<Film> response = entries.stream().map(Map.Entry::getKey).toList();

        if (count == null || entries.size() > count) {
            return response.subList(0, 9 + 1);
        }

        return response;
    }
}
