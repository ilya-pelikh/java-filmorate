package ru.yandex.practicum.filmorate.social.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.social.like.entity.Like;
import ru.yandex.practicum.filmorate.social.like.service.LikeService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PutMapping(path = "/films/{filmId}/like/{userId}")
    public Like addLikeToFilmByUser(@PathVariable Long filmId, @PathVariable Long userId) {
        Like like = likeService.addLikeToFilmByUser(userId, filmId);

        log.trace("Добавление лайка {} -> {}", userId, filmId);

        return like;
    }

    @DeleteMapping(path = "/films/{filmId}/like/{userId}")
    public Like removeLikeFromFilmByUser(@PathVariable Long filmId, @PathVariable Long userId) {
        Like like = likeService.removeLikeFromFilmByUser(userId, filmId);

        log.trace("Удаление лайка {} -> {}", userId, filmId);

        return like;
    }

    @GetMapping(path = "/films/popular")
    public Collection<FilmResponseDto> getFilmsByPopularity(@RequestParam Integer count) {
        return likeService.getFilmsByPopularity(count);
    }
}
