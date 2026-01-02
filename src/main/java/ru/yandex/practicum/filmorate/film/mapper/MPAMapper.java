package ru.yandex.practicum.filmorate.film.mapper;

import ru.yandex.practicum.filmorate.film.domain.MPA;
import ru.yandex.practicum.filmorate.film.dto.MPARequestDto;
import ru.yandex.practicum.filmorate.film.dto.MPAResponseDto;

public class MPAMapper {
    public static MPA toDomain(MPARequestDto dto) {
        MPA mpa = new MPA();

        mpa.setId(dto.getId());

        return mpa;
    }

    public static MPAResponseDto toResponse(MPA genre) {
        MPAResponseDto dto = new MPAResponseDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
