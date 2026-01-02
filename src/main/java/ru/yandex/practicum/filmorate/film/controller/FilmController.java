package ru.yandex.practicum.filmorate.film.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.common.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.film.domain.Film;
import ru.yandex.practicum.filmorate.film.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;
import ru.yandex.practicum.filmorate.film.dto.MPAResponseDto;
import ru.yandex.practicum.filmorate.film.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.film.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping(path = "/films")
    public Collection<FilmResponseDto> getAllFilms() {
        log.info("-> Получение всех фильмов");

        Collection<FilmResponseDto> response = filmService.getAllFilms();

        log.info("-> Получены фильмы: {}", response);

        return response;
    }

    @GetMapping(path = "/films/{filmId}")
    public FilmResponseDto getFilmById(@PathVariable long filmId) {
        log.info("-> Получение фильма по id {}", filmId);

        FilmResponseDto response = filmService.getFilmById(filmId);

        log.info("-> Получен фильм по id {}", response);

        return response;
    }

    @PostMapping(path = "/films")
    public FilmResponseDto addFilm(@Valid @RequestBody FilmRequestDto dto) {
        log.info("-> Добавление фильма: {}", dto);

        Film film = FilmMapper.toDomain(dto);
        FilmResponseDto savedFilm = filmService.addFilm(film);

        log.info("-> Фильм добавлен: {}", dto);

        return savedFilm;
    }

    @PutMapping(path = "/films")
    public FilmResponseDto updateFilm(@Validated(ValidationGroups.OnUpdate.class) @RequestBody FilmRequestDto dto) {
        log.info("-> Обновление фильма: {}", dto);

        Film film = FilmMapper.toDomain(dto);
        FilmResponseDto response = filmService.editFilm(film);

        log.info("-> Фильм обновлен: {}", response);

        return response;
    }

    @GetMapping(path = "/genres")
    public List<GenreResponseDto> getGenres() {
        log.info("-> Получение жанров");

        List<GenreResponseDto> response = filmService.getAllGenres();

        log.info("-> Получены жанры: {}", response);
        return response;
    }

    @GetMapping(path = "/genres/{genreId}")
    public GenreResponseDto getGenreById(@PathVariable long genreId) {
        log.info("-> Получение жанров по id: {}", genreId);

        GenreResponseDto response = filmService.getGenreById(genreId);

        log.info("-> Получен жанр по id: {}", genreId);

        return response;
    }

    @GetMapping(path = "/mpa")
    public List<MPAResponseDto> getMPAs() {
        log.info("-> Получение mpa");

        List<MPAResponseDto> response = filmService.getAllMPAs();

        log.info("-> Получение mpa: {}", response);

        return response;
    }

    @GetMapping(path = "/mpa/{mpaId}")
    public MPAResponseDto getMPaById(@PathVariable long mpaId) {
        log.info("-> Получение mpa по id: {}", mpaId);

        MPAResponseDto response = filmService.getMPAById(mpaId);

        log.info("-> Получение mpa по id: {}", mpaId);

        return response;
    }

}
