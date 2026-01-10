package ru.yandex.practicum.filmorate.film.mapper;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.practicum.filmorate.film.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.film.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;
import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;

public class FilmMapper {
    public static Film toEntity(FilmRequestDto dto) {
        List<Genre> genreList = new ArrayList<>();
        if (dto.getGenres() != null) {
            genreList = dto.getGenres().stream()
                    .distinct()
                    .map(GenreMapper::toEntity)
                    .toList();
        }

        Film film = new Film(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getReleaseDate(),
                dto.getDuration(),
                MPAMapper.toEntity(dto.getMpa()),
                genreList);

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
