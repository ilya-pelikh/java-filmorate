package ru.yandex.practicum.filmorate.film.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Genre {
    private Long id;
    private String name;
}
