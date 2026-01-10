package ru.yandex.practicum.filmorate.film.mapper;

import ru.yandex.practicum.filmorate.film.dto.GenreRequestDto;
import ru.yandex.practicum.filmorate.film.dto.GenreResponseDto;
import ru.yandex.practicum.filmorate.film.entity.Genre;

public class GenreMapper {
    public static Genre toEntity(GenreRequestDto dto) {
        Genre genre = new Genre(dto.getId(), null);

        return genre;
    }

    public static GenreResponseDto toResponse(Genre genre) {
        GenreResponseDto dto = new GenreResponseDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
