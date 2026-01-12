package ru.yandex.practicum.filmorate.social.like.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcLikeRepository implements LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_LIKE_QUERY = """
            INSERT INTO user_like (user_id, film_id)
            VALUES (?, ?)
            """;

    private static final String DELETE_LIKE_BY_ID_QUERY = """
            DELETE FROM user_like
            WHERE user_id = ? AND film_id = ?
            """;

    private static final String GET_LIKES_BY_MAXIMUM = """
            SELECT film_id
            FROM user_like
            GROUP BY film_id
            ORDER BY COUNT(*) DESC
            LIMIT ?
            """;

    private static final String CHECK_LIKE_FOR_EXISTANCE_QUERY = "SELECT EXISTS(SELECT 1 FROM user_like WHERE user_id = ? AND film_id = ?)";

    @Override
    public void addLike(long userId, long filmId) {
        try {
            jdbcTemplate.update(INSERT_LIKE_QUERY, userId, filmId);
        } catch (DuplicateKeyException e) {
            throw new NotFoundException("Не удалось создать запись");
        }
    }

    @Override
    public void removeLike(long userId, long filmId) throws NotFoundException {
        int updatedRows = jdbcTemplate.update(DELETE_LIKE_BY_ID_QUERY, userId, filmId);

        if (updatedRows == 0) {
            throw new NotFoundException("Такого лайка не существует");
        }

    }

    @Override
    public List<Long> getLikesByMaximum(int limit) {
        return jdbcTemplate.query(GET_LIKES_BY_MAXIMUM, (rs, rowNum) -> rs.getLong("film_id"), limit);
    }

    public boolean checkLikeForExistance(Long userId, Long filmId) {
        return jdbcTemplate.queryForObject(
                CHECK_LIKE_FOR_EXISTANCE_QUERY,
                Boolean.class,
                userId, filmId);
    }

}
