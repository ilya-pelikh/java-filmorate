package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.social.friendship.domain.Friendship;

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
    public Friendship addFriendship(Friendship friendship) {
        try {
            jdbcTemplate.update(INSERT_FRIENDSHIP_QUERY, friendship.getUserId(), friendship.getFriendId());
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistException(String.format(
                    "Пользователи с id %s и %s уже являются друзьями",
                    friendship.getUserId(),
                    friendship.getFriendId()));
        }

        return friendship;
    }

    @Override
    public Friendship removeFriendship(Friendship friendship) {
        jdbcTemplate.update(DELETE_FRIENDSHIP_QUERY, friendship.getUserId(),
                friendship.getFriendId());

        return friendship;
    }

    @Override
    public List<Long> getFriendIdsForUserById(Long userId) {
        return jdbcTemplate.query(
                FIND_FRIEND_IDS_FROM_FRIENDSHIP_QUERY,
                (rs, rowNum) -> rs.getLong("friend_id"),
                userId);
    }

}
