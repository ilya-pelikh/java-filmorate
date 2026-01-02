package ru.yandex.practicum.filmorate.film.mapper;

import java.util.List;

import ru.yandex.practicum.filmorate.film.domain.Film;
import ru.yandex.practicum.filmorate.film.domain.Genre;
import ru.yandex.practicum.filmorate.film.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;

public class FilmMapper {
    public static Film toDomain(FilmRequestDto dto) {
        Film film = new Film();

        film.setId(dto.getId());
        film.setName(dto.getName());
        film.setDescription(dto.getDescription());
        film.setReleaseDate(dto.getReleaseDate());
        film.setDuration(dto.getDuration());

        if (dto.getGenres() != null) {
            List<Genre> genreList = dto.getGenres().stream()
                    .distinct()
                    .map(GenreMapper::toDomain)
                    .toList();
            film.setGenres(genreList);
        }

        film.setMpa(MPAMapper.toDomain(dto.getMpa()));

        return film;
    }

    public static FilmResponseDto toResponse(Film film) {
        FilmResponseDto dto = new FilmResponseDto();

        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());

        if (film.getGenres() != null) {
            List<GenreResponseDto> genreList = film.getGenres().stream()
                    .map(GenreMapper::toResponse)
                    .toList();
            dto.setGenres(genreList);
        }

        dto.setMpa(MPAMapper.toResponse(film.getMpa()));

        return dto;
    }
}
