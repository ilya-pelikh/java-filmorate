package ru.yandex.practicum.filmorate.user.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.user.domain.User;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcUserRepositor implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_USERS_QUERY = """
            SELECT *
            FROM users
            """;
    private static final String FIND_USER_BY_ID_QUERY = FIND_ALL_USERS_QUERY + """
            WHERE id = ?
            """;

    private static final String FIND_USERS_BY_IDS_QUERY = FIND_ALL_USERS_QUERY + """
            WHERE id in (%s)
            """;

    private static final String INSERT_USER_QUERY = """
            INSERT INTO users (email, login, name, birthday)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_USER_BY_ID_QUERY = """
            UPDATE users
            SET email = ?, login = ?, name = ?, birthday = ?
            WHERE id = ?
            """;

    private static final String CHECK_USER_BY_EXISTANCE_QUERY = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";

    @Override
    public User getById(long userId) throws NotFoundException {
        try {
            return jdbcTemplate.queryForObject(FIND_USER_BY_ID_QUERY, userRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Пользователя с id = %s не существует", userId));
        }
    }

    @Override
    public List<User> getUsersByIDs(List<Long> ids) {
        String idPlaceholders = ids.stream()
                .map(obj -> "?")
                .collect(Collectors.joining(","));
        String sqlWithPlaceHolders = String.format(FIND_USERS_BY_IDS_QUERY, idPlaceholders);

        return jdbcTemplate.query(sqlWithPlaceHolders, userRowMapper, ids.toArray());
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(FIND_ALL_USERS_QUERY, userRowMapper);
    }

    @Override
    public Long add(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            setUserarameters(ps, user);
            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public Long edit(long id, User user) {
        jdbcTemplate.update(
                UPDATE_USER_BY_ID_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                id);

        return id;
    }

    @Override
    public boolean checkUserForExistance(Long userId) {
        return jdbcTemplate.queryForObject(
                CHECK_USER_BY_EXISTANCE_QUERY,
                Boolean.class,
                userId);
    }

    private void setUserarameters(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getLogin());
        ps.setString(3, user.getName());
        ps.setDate(4, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());

        return user;
    };

}