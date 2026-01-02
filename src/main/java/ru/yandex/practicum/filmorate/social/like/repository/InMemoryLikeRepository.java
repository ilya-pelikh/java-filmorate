package ru.yandex.practicum.filmorate.social.like.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.social.like.domain.Like;

@Component
public class InMemoryLikeRepository implements LikeRepository {

    @Override
    public Like findLikeById(Long likeId) throws NotFoundException {

        throw new UnsupportedOperationException("Unimplemented method 'findLikeById'");
    }

    @Override
    public boolean checkLikeForExistance(Long userId, Long filmId) {

        throw new UnsupportedOperationException("Unimplemented method 'checkLikeForExistance'");
    }

    @Override
    public List<Long> getLikesByMaximum(int limit) {

        throw new UnsupportedOperationException("Unimplemented method 'getLikesByMaximum'");
    }

    @Override
    public Like addLike(Like like) {

        throw new UnsupportedOperationException("Unimplemented method 'addLike'");
    }

    @Override
    public Like removeLike(Like like) {

        throw new UnsupportedOperationException("Unimplemented method 'removeLike'");
    }

}
