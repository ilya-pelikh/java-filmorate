package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;

@Primary
@Service
@RequiredArgsConstructor
public class JdbcFriendshipRepository implements FriendshipRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_FRIENDSHIP_QUERY = """
            INSERT INTO friendship (user_id, friend_id)
            VALUES (?, ?)
            """;
    private static final String DELETE_FRIENDSHIP_QUERY = """
            DELETE FROM friendship
            WHERE user_id = ? AND friend_id = ?
            """;
    private static final String FIND_FRIEND_IDS_FROM_FRIENDSHIP_QUERY = """
            SELECT friend_id
            FROM friendship as f
            WHERE f.user_id = ?
            """;

    @Override
    public void addFriendship(Long userId, Long friendId) {
        try {
            jdbcTemplate.update(INSERT_FRIENDSHIP_QUERY, userId, friendId);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistException("Пользователи уже являются друзьями");
        }

    }

    @Override
    public void removeFriendship(Long userId, Long friendId) {
        jdbcTemplate.update(DELETE_FRIENDSHIP_QUERY, userId, friendId);
    }

    @Override
    public List<Long> getFriendIdsForUserById(Long userId) {
        return jdbcTemplate.query(
                FIND_FRIEND_IDS_FROM_FRIENDSHIP_QUERY,
                (rs, rowNum) -> rs.getLong("friend_id"),
                userId);
    }

}
