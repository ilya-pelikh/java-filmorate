package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Friendship {
    private long id;
    private User user;
    private User friend;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(user, that.user) && Objects.equals(friend, that.friend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, friend);
    }
}
