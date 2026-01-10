package ru.yandex.practicum.filmorate.social.friendship.entity;

import lombok.AllArgsConstructor;

import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Friendship {
    private Long userId;
    private Long friendId;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Friendship)) {
            return false;
        }
        Friendship friendship = (Friendship) o;
        return Objects.equals(userId, friendship.userId) && Objects.equals(friendId, friendship.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }

}
