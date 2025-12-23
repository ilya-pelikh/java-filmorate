package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.validation.annotations.AfterDate;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return duration == film.duration && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, releaseDate, duration);
    }
}
