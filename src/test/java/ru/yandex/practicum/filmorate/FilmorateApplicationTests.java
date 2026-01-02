package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.yandex.practicum.filmorate.user.domain.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
class FilmorateApplicationTests {

    private final UserRepository userStorage;

    @Autowired
    public FilmorateApplicationTests(UserRepository userStorage) {
        this.userStorage = userStorage;
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setName("Джек");
        user.setLogin("Воробей");
        user.setEmail("vorobey@mail.ru");
        user.setBirthday(LocalDate.of(2012, 12, 12));

        userStorage.add(user);

        User userOptional = userStorage.getById(1);

        assertThat(userOptional).hasFieldOrPropertyWithValue("id", 1L);
    }
}