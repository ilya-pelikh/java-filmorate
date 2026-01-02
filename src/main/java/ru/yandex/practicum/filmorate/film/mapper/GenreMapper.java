package ru.yandex.practicum.filmorate.film.mapper;

import ru.yandex.practicum.filmorate.film.domain.Genre;
import ru.yandex.practicum.filmorate.film.dto.GenreRequestDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;

public class GenreMapper {
    public static Genre toDomain(GenreRequestDto dto) {
        Genre genre = new Genre();

        genre.setId(dto.getId());

        return genre;
    }

    public static GenreResponseDto toResponse(Genre genre) {
        GenreResponseDto dto = new GenreResponseDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
