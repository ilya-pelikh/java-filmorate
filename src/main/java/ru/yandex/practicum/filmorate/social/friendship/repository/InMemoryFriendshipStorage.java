package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.social.friendship.domain.Friendship;

@Component
public class InMemoryFriendshipStorage implements FriendshipRepository {

    @Override
    public Friendship addFriendship(Friendship friendship) throws AlreadyExistException {

        throw new UnsupportedOperationException("Unimplemented method 'addFriendship'");
    }

    @Override
    public Friendship removeFriendship(Friendship friendship) {

        throw new UnsupportedOperationException("Unimplemented method 'removeFriendship'");
    }

    @Override
    public List<Long> getFriendIdsForUserById(Long id) {

        throw new UnsupportedOperationException("Unimplemented method 'getFriendIdsForUserById'");
    }

}
