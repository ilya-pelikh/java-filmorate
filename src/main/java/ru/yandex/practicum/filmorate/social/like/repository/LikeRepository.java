package ru.yandex.practicum.filmorate.social.like.repository;

import java.util.List;

public interface LikeRepository {
    void addLike(long userId, long filmId);

    void removeLike(long userId, long filmId);

    boolean checkLikeForExistance(Long userId, Long filmId);

    List<Long> getLikesByMaximum(int limit);
}
