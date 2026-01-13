package ru.yandex.practicum.filmorate.social.friendship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.social.friendship.repository.FriendshipRepository;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final UserRepository userRepository;

    private final FriendshipRepository friendshipStorage;

    public void addUserToFriends(Long userId, Long friendId) {
        userRepository.getById(userId);
        userRepository.getById(friendId);

        friendshipStorage.addFriendship(userId, friendId);
    }

    public void removeUserFromFriends(Long userId, Long friendId) {
        userRepository.getById(userId);
        userRepository.getById(friendId);

        friendshipStorage.removeFriendship(userId, friendId);
    }

    public Collection<User> getFriendsByUserId(Long userId) {
        userRepository.getById(userId);

        List<Long> friendsIds = friendshipStorage.getFriendIdsForUserById(userId);

        List<User> response = userRepository.getUsersByIDs(friendsIds);

        return response;
    }

    public Collection<User> getCommonUserFriendsByUserId(long userId, long friendId) {
        List<Long> friendsForUserIds = friendshipStorage.getFriendIdsForUserById(userId);
        List<Long> friendsForFriendIds = friendshipStorage.getFriendIdsForUserById(friendId);

        List<Long> userFriendsCopy = new ArrayList<>(friendsForUserIds);

        userFriendsCopy.retainAll(friendsForFriendIds);

        List<User> response = userRepository.getUsersByIDs(userFriendsCopy);

        return response;
    }
}
