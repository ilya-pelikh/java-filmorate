package ru.yandex.practicum.filmorate.film.repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;
import ru.yandex.practicum.filmorate.film.entity.MPA;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class FilmRepositoryTests {

    private final FilmRepository repository;

    @Autowired
    public FilmRepositoryTests(FilmRepository userStorage) {
        this.repository = userStorage;
    }

    @Test
    public void filmRepository_addAndFindFilm() {
        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");
        Film film = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));

        Film addedFilm = repository.addFilm(film);

        assertThat(repository.findFilmById(addedFilm.getId())).isEqualTo(film);
    }

    @Test
    public void filmRepository_findAllFilms() {
        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");

        Film film1 = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));

        Film film2 = new Film(
                null,
                "Джек Водогрей 2",
                "Про пирата",
                LocalDate.of(2013, 12, 12), 130,
                mpa,
                List.of(genre));

        Film addedFilm1 = repository.addFilm(film1);

        Film addedFilm2 = repository.addFilm(film2);

        List<Film> expected = List.of(addedFilm1, addedFilm2);

        assertThat(repository.findAllFilms()).isEqualTo(expected);
    }

    @Test
    public void filmRepository_editFilm() {
        MPA mpa = new MPA(1L, "G");
        Film film = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        Film addedFilm = repository.addFilm(film);

        Film film2 = new Film(
                addedFilm.getId(),
                "Джек Водогрей 2",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        repository.editFilm(addedFilm.getId(), film2);

        assertThat(repository.findFilmById(addedFilm.getId())).isEqualTo(film2);
    }

    @Test
    public void filmRepository_checkMpaByExist() {
        MPA mpa = new MPA(1L, "G");
        Film film = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        repository.addFilm(film);

        boolean isMPAExist = repository.checkMpaByExist(film);

        assertThat(isMPAExist).isEqualTo(true);
    }

    @Test
    public void filmRepository_checkGanreByExist() {
        MPA mpa = new MPA(1L, "G");
        Film film = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        Film addedFilm = repository.addFilm(film);

        boolean isGenreExist = repository.checkGenreByExist(repository.findFilmById(addedFilm.getId()));
        assertThat(isGenreExist).isEqualTo(true);
    }

    @Test
    public void filmRepository_getAllGenres() {
        List<Genre> basicGenres = List.of(
                new Genre(1L, "Комедия"),
                new Genre(2L, "Драма"),
                new Genre(3L, "Мультфильм"),
                new Genre(4L, "Триллер"),
                new Genre(5L, "Документальный"),
                new Genre(6L, "Боевик"));
        List<Genre> genres = repository.getAllGenres();

        assertThat(genres).isEqualTo(basicGenres);
    }

    @Test
    public void filmRepository_getGenreById() {
        Genre genre = new Genre(1L, "Комедия");
        Genre foundGenre = repository.getGenreById(genre.getId());

        assertThat(genre).isEqualTo(foundGenre);
    }

    @Test
    public void filmRepository_getAllMPAs() {
        List<MPA> basicMPAs = List.of(
                new MPA(null, "G"),
                new MPA(null, "PG"),
                new MPA(null, "PG-13"),
                new MPA(null, "R"),
                new MPA(null, "NC-17"));

        List<MPA> mpas = repository.getAllMPAs();

        assertThat(mpas).isEqualTo(basicMPAs);
    }

    @Test
    public void filmRepository_getMPAById() {
        MPA basicMPA = new MPA(1L, "G");

        MPA mpa = repository.getMPAById(basicMPA.getId());

        assertThat(mpa).isEqualTo(basicMPA);
    }

    @Test
    public void filmRepository_checkFilmForExistance() {
        MPA mpa = new MPA(1L, "G");
        Film film = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        Film addedFilm = repository.addFilm(film);
        assertThat(repository.checkFilmForExistance(addedFilm.getId())).isEqualTo(true);
    }

    @Test
    public void filmRepository_getFilmsByIDs() {
        MPA mpa = new MPA(1L, "G");

        Film film1 = new Film(
                null,
                "Джек Водогрей",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of());

        Film film2 = new Film(
                null,
                "Джек Водогрей 2",
                "Про пирата",
                LocalDate.of(2013, 12, 12), 130,
                mpa,
                List.of());

        Film addedFilm1 = repository.addFilm(film1);
        Film addedFilm2 = repository.addFilm(film2);

        List<Film> expected = List.of(film1, film2);

        assertThat(repository.getFilmsByIDs(List.of(addedFilm1.getId(), addedFilm2.getId()))).isEqualTo(expected);
    }
}