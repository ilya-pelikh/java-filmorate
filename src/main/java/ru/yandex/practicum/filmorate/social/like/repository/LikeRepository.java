package ru.yandex.practicum.filmorate.social.like.repository;

import java.util.List;

import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.social.like.domain.Like;

public interface LikeRepository {
    Like addLike(Like like);

    Like removeLike(Like like);

    Like findLikeById(Long likeId) throws NotFoundException;

    boolean checkLikeForExistance(Long userId, Long filmId);

    List<Long> getLikesByMaximum(int limit);
}
