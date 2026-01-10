package ru.yandex.practicum.filmorate.film.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@ToString
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private MPA mpa;
    private List<Genre> genres = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Film)) {
            return false;
        }
        Film film = (Film) o;
        return Objects.equals(name, film.name) && Objects.equals(description, film.description)
                && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(duration, film.duration)
                && Objects.equals(mpa, film.mpa) && Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, releaseDate, duration, mpa, genres);
    }
}
