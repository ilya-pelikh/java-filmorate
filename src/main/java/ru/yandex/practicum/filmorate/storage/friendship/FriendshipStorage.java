package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.Collection;

public interface FriendshipStorage {
    Friendship add(Friendship friendship);

    Friendship remove(Friendship friendship);

    boolean contains(Friendship friendship);

    Collection<Friendship> getAll();
}


