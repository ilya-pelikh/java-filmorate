package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final LikeService likeService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }


    @GetMapping(path = "/{id}")
    public Film getFilmById(@PathVariable long id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        Film savedFilm = filmService.addFilm(film);

        log.trace("Добавление фильма{}->{}", savedFilm.getId(), film);

        return savedFilm;
    }

    @PutMapping
    public Film updateFilm(@Validated(ValidationGroups.OnUpdate.class) @RequestBody Film film) {
        Film editedFilm = filmService.editFilm(film.getId(), film);

        log.trace("Обновление фильма{}->{}", editedFilm.getId(), film);

        return editedFilm;
    }

    @PutMapping(path = "/{filmId}/like/{userId}")
    public Like addLikeToFilmByUser(@PathVariable long filmId, @PathVariable long userId) {
       Like like = likeService.addLikeToFilmByUser(userId, filmId);

       log.trace("Добавление лайка {}->{}", userId, like.getId());

       return like;
    }

    @DeleteMapping(path = "/{filmId}/like/{userId}")
    public Like removeLikeFromFilmByUser(@PathVariable long filmId, @PathVariable long userId) {
        Like like = likeService.removeLikeFromFilmByUser(userId, filmId);

        log.trace("Удаление лайка {}->{}", userId, like.getId());

        return like;
    }

    @GetMapping(path = "/popular")
    public Collection<Film> getFilmsByPopularity(@RequestParam int count) {
        return likeService.getFilmsByPopularity(count);
    }
}
