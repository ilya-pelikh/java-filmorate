package ru.yandex.practicum.filmorate.social.like.repository;

import java.util.List;

import ru.yandex.practicum.filmorate.social.like.entity.Like;

public interface LikeRepository {
    Like addLike(Like like);

    Like removeLike(Like like);

    boolean checkLikeForExistance(Long userId, Long filmId);

    List<Long> getLikesByMaximum(int limit);
}
