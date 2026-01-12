package ru.yandex.practicum.filmorate.film.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;
import ru.yandex.practicum.filmorate.film.entity.MPA;

@Repository
@RequiredArgsConstructor
@Primary
public class JdbcFilmRepository implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_FILMS_QUERY = """
            SELECT
                f.*,
                mpa.name as mpa_name
            FROM films f
            LEFT JOIN mpa ON f.mpa_id = mpa.id
            """;
    private static final String FIND_FILM_BY_ID_QUERY = FIND_ALL_FILMS_QUERY + "WHERE f.id = ?";
    private static final String FIND_FILMS_BY_IDS_QUERY = FIND_ALL_FILMS_QUERY + "WHERE f.id in (%s)";
    private static final String UPDATE_FILM_BY_ID_QUERY = """
            UPDATE films
            SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?
            WHERE id = ?
            """;
    private static final String INSERT_FILM_QUERY = """
            INSERT INTO films (name, description, release_date, duration, mpa_id)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String INSERT_FILM_GENRE_QUERY = """
            INSERT INTO film_genre (film_id, genre_id)
            VALUES (?, ?)
            """;
    private static final String DELETE_FILM_GENRE_QUERY = """
            DELETE FROM film_genre
            WHERE film_id = ?
            """;
    private static final String FIND_ALL_GENRES_QUERY = """
            SELECT id, name
            FROM genres
            ORDER BY id
            """;
    private static final String FIND_GENRE_BY_ID_QUERY = """
            SELECT id, name
            FROM genres
            WHERE id = ?
            ORDER BY id
            """;

    private static final String FIND_GENRES_BY_FILM_ID_QUERY = """
            SELECT g.id, g.name
            FROM film_genre as fg
            LEFT JOIN genres AS g ON g.id = fg.genre_id
            WHERE fg.film_id = ?
            """;

    private static final String FIND_GENRES_BY_FILM_IDS_QUERY = """
            SELECT fg.film_id, g.id, g.name
            FROM film_genre as fg
            LEFT JOIN genres AS g ON g.id = fg.genre_id
            WHERE fg.film_id IN (%s)
            ORDER BY fg.film_id, g.id
            """;

    private static final String FIND_ALL_MPAS_QUERY = """
            SELECT id, name
            FROM mpa
            ORDER BY id
            """;
    private static final String FIND_MPA_BY_ID_QUERY = """
            SELECT id, name
            FROM mpa
            WHERE id = ?
            ORDER BY id
            """;

    private static final String CHECK_FILM_BY_EXIST_QUERY = "SELECT EXISTS(SELECT 1 FROM films WHERE id = ?)";
    private static final String CHECK_MPA_BY_EXIST_QUERY = "SELECT EXISTS(SELECT 1 FROM mpa WHERE id = ?)";
    private static final String CHECK_GENRE_BY_EXIST_QUERY = "SELECT COUNT(DISTINCT id) FROM genres WHERE id IN (%s)";

    private final RowMapper<Film> filmRowMapper = (rs, rowNum) -> {

        Film film = new Film(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                null,
                null);

        return film;
    };

    private final RowMapper<Film> filmWithMpaRowMapper = (rs, rowNum) -> {
        Film film = filmRowMapper.mapRow(rs, rowNum);

        Long mpaId = rs.getLong("mpa_id");
        String mpaName = rs.getString("mpa_name");

        return new Film(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                new MPA(mpaId, mpaName),
                film.getGenres());

    };

    private final RowMapper<Genre> genreRowMapper = (rs, rowNum) -> {
        Genre genre = new Genre(
                rs.getLong("id"),
                rs.getString("name"));

        return genre;
    };

    private final RowMapper<MPA> mpaMapper = (rs, rowNum) -> {
        return new MPA(rs.getLong("id"), rs.getString("name"));
    };

    private final ResultSetExtractor<Map<Long, List<Genre>>> genreByFilmMapper = rs -> {
        Map<Long, List<Genre>> result = new HashMap<>();
        while (rs.next()) {
            Long filmId = rs.getLong("film_id");
            Genre genre = genreRowMapper.mapRow(rs, rs.getRow());
            result.computeIfAbsent(filmId, k -> new ArrayList<>()).add(genre);
        }
        return result;
    };

    @Override
    public Film findFilmById(long id) throws NotFoundException {
        try {
            Film film = jdbcTemplate.queryForObject(FIND_FILM_BY_ID_QUERY, filmWithMpaRowMapper, id);
            List<Genre> genres = jdbcTemplate.query(
                    FIND_GENRES_BY_FILM_ID_QUERY,
                    genreRowMapper,
                    id);

            return new Film(
                    film.getId(),
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa(),
                    genres);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Фильм с id = %s не найден", id));
        }

    }

    @Override
    public Film addFilm(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_FILM_QUERY, Statement.RETURN_GENERATED_KEYS);
            setFilmParameters(ps, film);
            return ps;
        }, keyHolder);

        Long filmId = keyHolder.getKeyAs(Long.class);

        addGenresToFilm(filmId, film.getGenres());

        return new Film(
                filmId,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(),
                film.getGenres());
    }

    @Override
    public Film editFilm(Long filmId, Film film) {
        jdbcTemplate.update(
                UPDATE_FILM_BY_ID_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                filmId);

        removeGenresToFilm(filmId);

        addGenresToFilm(filmId, film.getGenres());

        return new Film(filmId,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(),
                film.getGenres());
    }

    @Override
    public Collection<Film> findAllFilms() {
        List<Film> films = jdbcTemplate.query(FIND_ALL_FILMS_QUERY, filmWithMpaRowMapper);

        String placeholders = films.stream()
                .map(obj -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format(FIND_GENRES_BY_FILM_IDS_QUERY, placeholders);

        List<Long> filmIds = films.stream().map(film -> film.getId()).toList();

        Map<Long, List<Genre>> genres = jdbcTemplate.query(sql, genreByFilmMapper, filmIds.toArray());

        return films.stream().map(film -> {
            return new Film(
                    film.getId(),
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa(),
                    genres.getOrDefault(film.getId(), Collections.emptyList()));
        }).toList();

    }

    public boolean checkMpaByExist(Film film) {
        return jdbcTemplate.queryForObject(
                CHECK_MPA_BY_EXIST_QUERY,
                Boolean.class,
                film.getMpa().getId());
    }

    public boolean checkFilmForExistance(Long filmId) {
        return jdbcTemplate.queryForObject(
                CHECK_FILM_BY_EXIST_QUERY,
                Boolean.class,
                filmId);
    }

    public boolean checkGenreByExist(Film film) {
        if (film.getGenres() == null) {
            return false;
        }

        List<Long> ids = film.getGenres().stream().map(genre -> genre.getId()).toList();
        String placeholders = ids.stream()
                .map(obj -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format(
                CHECK_GENRE_BY_EXIST_QUERY,
                placeholders);

        Long count = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                ids.toArray());

        return count != null && count == ids.size();
    }

    public void addGenresToFilm(Long filmId, List<Genre> genres) {
        List<Object[]> batchArgs = genres.stream()
                .map(genre -> new Object[] { filmId, genre.getId() })
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(INSERT_FILM_GENRE_QUERY, batchArgs);
    }

    public void removeGenresToFilm(Long filmId) {
        jdbcTemplate.update(DELETE_FILM_GENRE_QUERY, filmId);
    }

    private void setFilmParameters(PreparedStatement ps, Film film) throws SQLException {
        ps.setString(1, film.getName());
        ps.setString(2, film.getDescription());
        ps.setDate(3, film.getReleaseDate() != null ? Date.valueOf(film.getReleaseDate()) : null);
        ps.setInt(4, film.getDuration());
        ps.setLong(5, film.getMpa().getId());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query(FIND_ALL_GENRES_QUERY, genreRowMapper);
    }

    @Override
    public Genre getGenreById(Long genreId) {
        try {
            return jdbcTemplate.queryForObject(FIND_GENRE_BY_ID_QUERY, genreRowMapper, genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Жанр с id = %s не найден", genreId));
        }
    }

    @Override
    public List<MPA> getAllMPAs() {
        return jdbcTemplate.query(FIND_ALL_MPAS_QUERY, mpaMapper);
    }

    @Override
    public MPA getMPAById(long mpaId) {
        try {
            return jdbcTemplate.queryForObject(FIND_MPA_BY_ID_QUERY, mpaMapper, mpaId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("MPA с id = %s не найден", mpaId));
        }
    }

    @Override
    public List<Film> getFilmsByIDs(List<Long> ids) {
        String idPlaceholders = ids.stream()
                .map(obj -> "?")
                .collect(Collectors.joining(","));
        String sqlWithPlaceHolders = String.format(FIND_FILMS_BY_IDS_QUERY, idPlaceholders);

        List<Film> films = jdbcTemplate.query(sqlWithPlaceHolders, filmWithMpaRowMapper, ids.toArray());

        String placeholders = films.stream()
                .map(obj -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format(FIND_GENRES_BY_FILM_IDS_QUERY, placeholders);

        List<Long> filmIds = films.stream().map(film -> film.getId()).toList();

        Map<Long, List<Genre>> genres = jdbcTemplate.query(sql, genreByFilmMapper, filmIds.toArray());

        List<Film> mappedFilms = films.stream().map(film -> {
            return new Film(
                    film.getId(),
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa(),
                    genres.getOrDefault(film.getId(), Collections.emptyList()));
        }).toList();

        List<Film> result = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Long sortedId = ids.get(i);
            for (Film film : mappedFilms) {
                if (film.getId().equals(sortedId)) {
                    result.add(film);
                    continue;
                }
            }
        }

        return result;
    }

}
