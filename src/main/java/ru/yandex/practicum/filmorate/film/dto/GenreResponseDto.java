package ru.yandex.practicum.filmorate.film.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GenreResponseDto {
    private Long id;
    private String name;
}
