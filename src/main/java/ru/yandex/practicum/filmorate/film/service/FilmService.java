package ru.yandex.practicum.filmorate.film.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.common.exception.ValidationException;
import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;
import ru.yandex.practicum.filmorate.film.dto.MPAResponseDto;
import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;
import ru.yandex.practicum.filmorate.film.entity.MPA;
import ru.yandex.practicum.filmorate.film.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.film.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.film.mapper.MPAMapper;
import ru.yandex.practicum.filmorate.film.repository.FilmRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository storage;

    public Collection<FilmResponseDto> getAllFilms() {
        return storage.findAllFilms().stream().map(FilmMapper::toResponse).toList();
    }

    public boolean checkFilmForExistance(Long filmId) {
        return storage.checkFilmForExistance(filmId);
    }

    public FilmResponseDto getFilmById(long id) throws NotFoundException {
        Film foundFilm = storage.findFilmById(id);

        return FilmMapper.toResponse(foundFilm);
    }

    public List<FilmResponseDto> getFilmsByIDs(List<Long> ids) {
        List<Film> response = storage.getFilmsByIDs(ids);
        return response.stream().map(FilmMapper::toResponse).toList();
    }

    public FilmResponseDto addFilm(Film film) throws NotFoundException {
        boolean isMPAExist = storage.checkMpaByExist(film);

        if (!isMPAExist) {
            throw new NotFoundException(String.format("MPA с id=%s не существует", film.getMpa().getId()));
        }

        boolean isGenreExist = storage.checkGenreByExist(film);

        if (!isGenreExist) {
            throw new NotFoundException("Жанра с одним из id не существует");
        }

        Film addedFilm = storage.addFilm(film);

        return FilmMapper.toResponse(addedFilm);
    }

    public FilmResponseDto editFilm(Film film) throws ValidationException, NotFoundException {
        boolean isEditedFilmExist = storage.checkFilmForExistance(film.getId());

        if (!isEditedFilmExist) {
            throw new NotFoundException(String.format("Фильма с id = %s не существует", film.getId()));
        }

        Film editedFilm = storage.editFilm(film.getId(), film);

        return FilmMapper.toResponse(editedFilm);
    }

    public List<GenreResponseDto> getAllGenres() {
        return storage.getAllGenres().stream().map(GenreMapper::toResponse).toList();
    }

    public GenreResponseDto getGenreById(long genreId) {
        Genre genre = storage.getGenreById(genreId);
        return GenreMapper.toResponse(genre);
    }

    public List<MPAResponseDto> getAllMPAs() {
        return storage.getAllMPAs().stream().map(MPAMapper::toResponse).toList();
    }

    public MPAResponseDto getMPAById(long mpaId) {
        MPA mpa = storage.getMPAById(mpaId);
        return MPAMapper.toResponse(mpa);
    }
}
