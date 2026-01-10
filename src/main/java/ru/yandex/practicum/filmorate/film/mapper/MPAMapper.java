package ru.yandex.practicum.filmorate.film.mapper;

import ru.yandex.practicum.filmorate.film.dto.MPARequestDto;
import ru.yandex.practicum.filmorate.film.dto.MPAResponseDto;
import ru.yandex.practicum.filmorate.film.entity.MPA;

public class MPAMapper {
    public static MPA toEntity(MPARequestDto dto) {
        MPA mpa = new MPA(dto.getId(), null);

        return mpa;
    }

    public static MPAResponseDto toResponse(MPA genre) {
        MPAResponseDto dto = new MPAResponseDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
