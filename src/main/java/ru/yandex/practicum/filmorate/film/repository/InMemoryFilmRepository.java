package ru.yandex.practicum.filmorate.film.repository;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.film.domain.Film;
import ru.yandex.practicum.filmorate.film.domain.Genre;
import ru.yandex.practicum.filmorate.film.domain.MPA;

import java.util.Collection;
import java.util.List;

@Component
public class InMemoryFilmRepository implements FilmRepository {

    @Override
    public Film findFilmById(long id) throws NotFoundException {

        throw new UnsupportedOperationException("Unimplemented method 'findFilmById'");
    }

    @Override
    public Collection<Film> findAllFilms() {

        throw new UnsupportedOperationException("Unimplemented method 'findAllFilms'");
    }

    @Override
    public Long addFilm(Film film) {

        throw new UnsupportedOperationException("Unimplemented method 'addFilm'");
    }

    @Override
    public Long editFilm(long id, Film film) {

        throw new UnsupportedOperationException("Unimplemented method 'editFilm'");
    }

    @Override
    public boolean checkMpaByExist(Film film) {

        throw new UnsupportedOperationException("Unimplemented method 'checkMpaByExist'");
    }

    @Override
    public boolean checkGenreByExist(Film film) {

        throw new UnsupportedOperationException("Unimplemented method 'checkGenreByExist'");
    }

    @Override
    public void addGenresToFilm(Long filmId, List<Genre> genres) {

        throw new UnsupportedOperationException("Unimplemented method 'addGenresToFilm'");
    }

    @Override
    public Genre getGenreById(Long genreId) {

        throw new UnsupportedOperationException("Unimplemented method 'getGenreById'");
    }

    @Override
    public List<Genre> getAllGenres() {

        throw new UnsupportedOperationException("Unimplemented method 'getAllGenres'");
    }

    @Override
    public List<MPA> getAllMPAs() {

        throw new UnsupportedOperationException("Unimplemented method 'getAllMPAs'");
    }

    @Override
    public MPA getMPAById(long mpaId) {

        throw new UnsupportedOperationException("Unimplemented method 'getMPAById'");
    }

    @Override
    public boolean checkFilmForExistance(Long filmId) {

        throw new UnsupportedOperationException("Unimplemented method 'checkFilmForExistance'");
    }

    @Override
    public List<Film> getFilmsByIDs(List<Long> ids) {

        throw new UnsupportedOperationException("Unimplemented method 'getFilmsByIDs'");
    }

}
