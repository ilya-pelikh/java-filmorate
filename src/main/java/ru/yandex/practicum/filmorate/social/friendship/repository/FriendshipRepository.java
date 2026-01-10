package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.util.List;

import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.social.friendship.entity.Friendship;

public interface FriendshipRepository {
    Friendship addFriendship(Friendship friendship) throws AlreadyExistException;

    Friendship removeFriendship(Friendship friendship);

    List<Long> getFriendIdsForUserById(Long id);
}
