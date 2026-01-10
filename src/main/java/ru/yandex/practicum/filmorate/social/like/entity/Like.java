package ru.yandex.practicum.filmorate.social.like.entity;

import lombok.AllArgsConstructor;

import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Like {
    private Long userId;
    private Long filmId;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Like)) {
            return false;
        }
        Like like = (Like) o;
        return Objects.equals(userId, like.userId) && Objects.equals(filmId, like.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, filmId);
    }

}
