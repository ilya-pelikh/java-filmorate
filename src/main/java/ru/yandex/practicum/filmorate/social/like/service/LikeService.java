package ru.yandex.practicum.filmorate.social.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.film.service.FilmService;
import ru.yandex.practicum.filmorate.social.like.repository.LikeRepository;
import ru.yandex.practicum.filmorate.user.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    private final FilmService filmService;
    private final UserService userService;

    public void addLikeToFilmByUser(long userId, long filmId) {
        Boolean isUserExist = userService.checkUserForExistance(userId);

        if (!isUserExist) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        Boolean isFilmExist = filmService.checkFilmForExistance(filmId);

        if (!isFilmExist) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", filmId));
        }

        boolean isLikeExist = likeRepository.checkLikeForExistance(userId, filmId);
        if (isLikeExist) {
            throw new AlreadyExistException("Лайк уже был поставлен");
        }

        likeRepository.addLike(userId, filmId);
    }

    public void removeLikeFromFilmByUser(long userId, long filmId) {
        Boolean isUserExist = userService.checkUserForExistance(userId);

        if (!isUserExist) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        Boolean isFilmExist = filmService.checkFilmForExistance(filmId);

        if (!isFilmExist) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", filmId));
        }

        boolean isLikeExist = likeRepository.checkLikeForExistance(userId, filmId);
        if (!isLikeExist) {
            throw new AlreadyExistException("Лайк уже был поставлен");
        }

        likeRepository.removeLike(userId, filmId);
    }

    public Collection<FilmResponseDto> getFilmsByPopularity(Integer count) {
        List<Long> filmIds = likeRepository.getLikesByMaximum(count == null ? 10 : count);

        List<FilmResponseDto> films = filmService.getFilmsByIDs(filmIds);

        return films;
    }
}
