package ru.yandex.practicum.filmorate.social.friendship.repository;

import java.util.List;

import ru.yandex.practicum.filmorate.common.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;

public interface FriendshipRepository {
    void addFriendship(Long userId, Long friendId) throws AlreadyExistException;

    void removeFriendship(Long userId, Long friendId) throws NotFoundException;

    List<Long> getFriendIdsForUserById(Long id);
}
