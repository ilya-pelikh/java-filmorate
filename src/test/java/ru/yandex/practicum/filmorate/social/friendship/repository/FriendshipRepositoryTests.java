package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.yandex.practicum.filmorate.social.friendship.entity.Friendship;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FriendshipRepositoryTests {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendshipRepositoryTests(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void friendshipStorage_addFriendship() {
        User user1 = new User(
                null,
                "voarobeqy1@mail.ru",
                "vorobzey1",
                "Джек",
                LocalDate.of(2012, 12, 12));
        User user2 = new User(
                null,
                "vorsobeyw@mail.ru",
                "vorovbey2",
                "Джек",
                LocalDate.of(2012, 12, 12));

        Long index1 = userRepository.addUser(user1);
        Long index2 = userRepository.addUser(user2);

        Friendship friendship = new Friendship(index1, index2);

        friendshipRepository.addFriendship(friendship);

        assertThat(friendshipRepository.getFriendIdsForUserById(index1)).isNotEmpty();
    }

    @Test
    public void friendshipStorage_removeFriendship() {
        User user1 = new User(
                null,
                "voroxbeqsy1@mail.ru",
                "voro1besy1",
                "Джек",
                LocalDate.of(2012, 12, 12));
        User user2 = new User(
                null,
                "vorzobe1yw@mail.ru",
                "vor1o1bey2",
                "Джек",
                LocalDate.of(2012, 12, 12));

        Long index1 = userRepository.addUser(user1);
        Long index2 = userRepository.addUser(user2);

        Friendship friendship = new Friendship(index1, index2);

        friendshipRepository.addFriendship(friendship);

        friendshipRepository.removeFriendship(friendship);

        assertThat(friendshipRepository.getFriendIdsForUserById(index1)).isEmpty();
    }

}