package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.validation.annotations.AfterDate;

import java.time.LocalDate;

@Data
public class Film {
    @NotNull(groups = ValidationGroups.OnUpdate.class)
    private long id;
    @NotBlank
    private String name;
    @NotBlank()
    @Size(max = 200)
    private String description;
    @NotNull
    @AfterDate(date = "1895-12-28")
    private LocalDate releaseDate;
    @NotNull
    @Min(1)
    private int duration;
}
