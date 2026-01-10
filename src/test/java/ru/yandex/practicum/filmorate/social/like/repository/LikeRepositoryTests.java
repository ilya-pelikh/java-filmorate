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
import ru.yandex.practicum.filmorate.social.like.entity.Like;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

// public interface LikeRepository {

//     List<Long> getLikesByMaximum(int limit);
// }

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

        Long indexOfUser = userRepository.addUser(user1);

        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");
        Film film = new Film(
                null,
                "Джек Водогрей 22",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));

        Long indexOfFilm = filmRepository.addFilm(film);

        Like likeToAdd = new Like(indexOfUser, indexOfFilm);

        likeRepository.addLike(likeToAdd);

        assertThat(likeRepository.checkLikeForExistance(indexOfUser, indexOfFilm)).isEqualTo(true);

        likeRepository.removeLike(likeToAdd);

        assertThat(likeRepository.checkLikeForExistance(indexOfUser, indexOfFilm)).isEqualTo(false);
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

        Long indexOfUser1 = userRepository.addUser(user1);
        Long indexOfUser2 = userRepository.addUser(user2);
        Long indexOfUser3 = userRepository.addUser(user3);

        MPA mpa = new MPA(1L, "G");
        Genre genre = new Genre(1L, "Комедия");
        Film film1 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));
        Long indexOfFilm1 = filmRepository.addFilm(film1);

        Film film2 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));
        Long indexOfFilm2 = filmRepository.addFilm(film2);

        Film film3 = new Film(
                null,
                "Джек Водогрей 1",
                "Про пирата",
                LocalDate.of(2012, 12, 12), 130,
                mpa,
                List.of(genre));
        Long indexOfFilm3 = filmRepository.addFilm(film3);

        Like likeToAdd1 = new Like(indexOfUser1, indexOfFilm3);
        likeRepository.addLike(likeToAdd1);
        Like likeToAdd2 = new Like(indexOfUser1, indexOfFilm2);
        likeRepository.addLike(likeToAdd2);
        Like likeToAdd3 = new Like(indexOfUser1, indexOfFilm1);
        likeRepository.addLike(likeToAdd3);

        Like likeToAdd4 = new Like(indexOfUser2, indexOfFilm3);
        likeRepository.addLike(likeToAdd4);
        Like likeToAdd5 = new Like(indexOfUser2, indexOfFilm2);
        likeRepository.addLike(likeToAdd5);

        Like likeToAdd6 = new Like(indexOfUser3, indexOfFilm3);
        likeRepository.addLike(likeToAdd6);

        List<Long> filmIds = likeRepository.getLikesByMaximum(2);

        assertThat(filmIds).isEqualTo(List.of(indexOfFilm3, indexOfFilm2));
    }
}
