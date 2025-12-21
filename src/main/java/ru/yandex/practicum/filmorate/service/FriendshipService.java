package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public Friendship addUserToFriends(long userId, long friendId) {
        User foundUser = userStorage.getById(userId);
        if (foundUser == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

        User foundUserFriend = userStorage.getById(friendId);
        if (foundUserFriend == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", friendId));
        }

        Friendship friendship = new Friendship();
        friendship.setUser(foundUser);
        friendship.setFriend(foundUserFriend);

        if (friendshipStorage.contains(friendship)) {
            throw new AlreadyExistException("Пользователи с id %s и %s уже являются друзьями");
        }

        return friendshipStorage.add(friendship);
    }

    public Friendship removeUserFromFriends(long userId, long friendId) {
        User foundUser = userStorage.getById(userId);
        if (foundUser == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

        User foundUserFriend = userStorage.getById(friendId);
        if (foundUserFriend == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", friendId));
        }

        Friendship friendship = new Friendship();
        friendship.setUser(foundUser);
        friendship.setFriend(foundUserFriend);

        return friendshipStorage.remove(friendship);
    }

    public Collection<User> getFriendsByUserId(long userId) {
        User foundUser = userStorage.getById(userId);
        if (foundUser == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

       return friendshipStorage.getAll().stream()
               .filter(
                       friendship -> friendship.getUser().getId() == userId || friendship.getFriend().getId() == userId
               ).map(friendship -> {
                   if (friendship.getUser().getId() == userId) return friendship.getFriend();
                   return friendship.getUser();
               }).toList();
    }

    public Collection<User> getCommonUserFriendsByUserId(long userId, long friendId) {
        User foundUser = userStorage.getById(userId);
        if (foundUser == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

        User foundUserFriend = userStorage.getById(friendId);
        if (foundUserFriend == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", friendId));
        }

        Collection<User> userFriends = friendshipStorage.getAll().stream()
                .filter(friendship -> friendship.getUser().getId() == userId)
                .map(Friendship::getFriend)
                .toList();

        Collection<User> friendFriends = friendshipStorage.getAll().stream()
                .filter(friendship -> friendship.getUser().getId() == friendId)
                .map(Friendship::getFriend)
                .toList();

        List<User> userFriendsCopy = new ArrayList<>(userFriends);

        userFriendsCopy.retainAll(friendFriends);

        return new HashSet<>(userFriendsCopy);
    }
}
