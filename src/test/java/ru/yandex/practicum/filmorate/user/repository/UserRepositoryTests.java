package ru.yandex.practicum.filmorate.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.yandex.practicum.filmorate.user.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTests {

        private final UserRepository userRepository;

        @Autowired
        public UserRepositoryTests(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Test
        public void userStorage_testEditUserById() {
                User user1 = new User(
                                null,
                                "vorobeyzaa@mail.ru",
                                "vorobeyz1",
                                "Джек",
                                LocalDate.of(2012, 12, 12));

                User addedUser = userRepository.addUser(user1);

                User user2 = new User(
                                null,
                                "ezmpt2y@mail.ru",
                                "vfxorobey",
                                "Джек",
                                LocalDate.of(2012, 12, 12));

                User editedUser = userRepository.editUser(addedUser.getId(), user2);

                assertThat(user2).isEqualTo(userRepository.getById(editedUser.getId()));
        }

        @Test
        public void userStorage_testAddAndFindUserById() {
                User user = new User(
                                null,
                                "vorobey@mail.ru",
                                "vorobey",
                                "Джек",
                                LocalDate.of(2012, 12, 12));

                User addedUser = userRepository.addUser(user);

                assertThat(user).isEqualTo(userRepository.getById(addedUser.getId()));
        }

        @Test
        public void userStorage_checkUserForExistance() {
                User user1 = new User(
                                null,
                                "vorobeqy122sz@mail.ru",
                                "vorobeyz122x",
                                "Джек",
                                LocalDate.of(2012, 12, 12));
                User user2 = new User(
                                null,
                                "vorobeyw22q@mail.ru",
                                "vorobey222q",
                                "Джек",
                                LocalDate.of(2012, 12, 12));

                User addedUser1 = userRepository.addUser(user1);
                User addedUser2 = userRepository.addUser(user2);

                assertThat(List.of(user1, user2)).isEqualTo(
                                userRepository.getUsersByIDs(List.of(addedUser1.getId(), addedUser2.getId())));
        }

        @Test
        public void userStorage_getUsersByIDs() {
                User user1 = new User(
                                null,
                                "vorobeqy122s@mail.ru",
                                "vorobey122x",
                                "Джек",
                                LocalDate.of(2012, 12, 12));

                User addedUser = userRepository.addUser(user1);

                boolean isUserExist = userRepository.checkUserForExistance(addedUser.getId());

                assertThat(isUserExist).isEqualTo(true);
        }
}