package ru.yandex.practicum.filmorate.film.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FilmResponseDto {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private MPAResponseDto mpa;
    private List<GenreResponseDto> genres = new ArrayList<>();
}
