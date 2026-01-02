package ru.yandex.practicum.filmorate.film.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.common.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.common.validation.annotations.AfterDate;

@Data
public class FilmRequestDto {
    @NotNull(groups = ValidationGroups.OnUpdate.class)
    private Long id;
    @NotBlank(message = "поле name не может быть пустым")
    private String name;
    @NotBlank()
    @Size(max = 200)
    private String description;
    @NotNull
    @AfterDate(date = "1895-12-28")
    private LocalDate releaseDate;
    @NotNull
    @Min(1)
    private Integer duration;
    @Valid
    private MPARequestDto mpa;
    @Valid
    private List<GenreRequestDto> genres;
}
