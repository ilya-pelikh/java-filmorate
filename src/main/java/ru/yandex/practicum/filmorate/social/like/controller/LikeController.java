package ru.yandex.practicum.filmorate.social.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.social.like.service.LikeService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PutMapping(path = "/films/{filmId}/like/{userId}")
    public void addLikeToFilmByUser(@PathVariable Long filmId, @PathVariable Long userId) {
        likeService.addLikeToFilmByUser(userId, filmId);

        log.trace("Добавление лайка {} -> {}", userId, filmId);
    }

    @DeleteMapping(path = "/films/{filmId}/like/{userId}")
    public void removeLikeFromFilmByUser(@PathVariable Long filmId, @PathVariable Long userId) {
        likeService.removeLikeFromFilmByUser(userId, filmId);

        log.trace("Удаление лайка {} -> {}", userId, filmId);
    }

    @GetMapping(path = "/films/popular")
    public Collection<FilmResponseDto> getFilmsByPopularity(@RequestParam Integer count) {
        return likeService.getFilmsByPopularity(count);
    }
}
