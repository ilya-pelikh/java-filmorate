package ru.yandex.practicum.filmorate.social.like.repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.yandex.practicum.filmorate.film.entity.Film;
import ru.yandex.practicum.filmorate.film.entity.Genre;
import ru.yandex.practicum.filmorate.film.entity.MPA;
import ru.yandex.practicum.filmorate.film.repository.FilmRepository;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class LikeRepositoryTests {
    private final LikeRepository likeRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeRepositoryTests(LikeRepository likeRepository, FilmRepository filmRepository,
            UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void likeRepository_addAndRemoveAndFinByIdLike() {
        User user1 = new User(
                null,
                "vorsaobeyzaa@mail.ru",
                "vorosabeyz1",
                "Джек",
                LocalDate.of(2012, 12, 12));

        User addedUser = userRepository.addUser(user1);

        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");
        Film film = new Film(
                null,
                "Джек Водогрей 22",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));

        Film addedFilm = filmRepository.addFilm(film);

        likeRepository.addLike(addedUser.getId(), addedFilm.getId());

        assertThat(likeRepository.checkLikeForExistance(addedUser.getId(), addedFilm.getId())).isEqualTo(true);

        likeRepository.removeLike(addedUser.getId(), addedFilm.getId());

        assertThat(likeRepository.checkLikeForExistance(addedUser.getId(), addedFilm.getId())).isEqualTo(false);
    }

    @Test
    public void likeRepository_getLikesByMaximum() {
        User user1 = new User(
                null,
                "vorssaobeyzaa@mail.ru",
                "vorossabeyz1",
                "Джек",
                LocalDate.of(2012, 12, 12));
        User user2 = new User(
                null,
                "vzorsxaobeyzaa@mail.ru",
                "vzoroxsabeyz1",
                "Джек",
                LocalDate.of(2012, 12, 12));
        User user3 = new User(
                null,
                "vzorsazozbeyzaa@mail.ru",
                "vzorozzsabeyz1",
                "Джек",
                LocalDate.of(2012, 12, 12));

        User addedUser1 = userRepository.addUser(user1);
        User addedUser2 = userRepository.addUser(user2);
        User addedUser3 = userRepository.addUser(user3);

        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");
        Film film1 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));
        Film addedFilm1 = filmRepository.addFilm(film1);

        Film film2 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));
        Film addedFilm2 = filmRepository.addFilm(film2);

        Film film3 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));

        Film addedFilm3 = filmRepository.addFilm(film3);

        likeRepository.addLike(addedUser1.getId(), addedFilm3.getId());
        likeRepository.addLike(addedUser1.getId(), addedFilm2.getId());
        likeRepository.addLike(addedUser1.getId(), addedFilm1.getId());

        likeRepository.addLike(addedUser2.getId(), addedFilm3.getId());
        likeRepository.addLike(addedUser2.getId(), addedFilm2.getId());

        likeRepository.addLike(addedUser3.getId(), addedFilm3.getId());

        List<Long> filmIds = likeRepository.getLikesByMaximum(2);

        assertThat(filmIds).isEqualTo(List.of(addedFilm3.getId(), addedFilm2.getId()));
    }
}
