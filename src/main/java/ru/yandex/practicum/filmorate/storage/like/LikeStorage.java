package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.Collection;

public interface LikeStorage {
    Like add(Like like);

    Like remove(Like like);

    boolean contains(Like like);

    Collection<Like> getAll();
}


