package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Like {
    private long id;
    private User user;
    private Film film;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(user, like.user) && Objects.equals(film, like.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, film);
    }
}
