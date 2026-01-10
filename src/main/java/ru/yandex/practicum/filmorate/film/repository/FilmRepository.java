package ru.yandex.practicum.filmorate.film.repository;

import java.util.Collection;
import java.util.List;

import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;
import ru.yandex.practicum.filmorate.film.entity.MPA;

public interface FilmRepository {
    Film findFilmById(long id) throws NotFoundException;

    Collection<Film> findAllFilms();

    Long addFilm(Film film);

    Long editFilm(Long id, Film film);

    boolean checkMpaByExist(Film film);

    boolean checkGenreByExist(Film film);

    void addGenresToFilm(Long filmId, List<Genre> genres);

    Genre getGenreById(Long genreId);

    List<Genre> getAllGenres();

    List<MPA> getAllMPAs();

    MPA getMPAById(long mpaId);

    boolean checkFilmForExistance(Long filmId);

    List<Film> getFilmsByIDs(List<Long> ids);
}
