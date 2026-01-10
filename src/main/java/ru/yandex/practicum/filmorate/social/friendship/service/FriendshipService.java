package ru.yandex.practicum.filmorate.social.friendship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.social.friendship.dto.FriendshipResponseDto;
import ru.yandex.practicum.filmorate.social.friendship.entity.Friendship;
import ru.yandex.practicum.filmorate.social.friendship.mapper.FriendshipMapper;
import ru.yandex.practicum.filmorate.social.friendship.repository.FriendshipRepository;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final UserService userService;

    private final FriendshipRepository friendshipStorage;

    public FriendshipResponseDto addUserToFriends(Friendship friendship) {
        userService.getUserById(friendship.getUserId());
        userService.getUserById(friendship.getFriendId());

        friendshipStorage.addFriendship(friendship);

        return FriendshipMapper.toResponse(friendship);
    }

    public Friendship removeUserFromFriends(Friendship friendship) {
        userService.getUserById(friendship.getUserId());
        userService.getUserById(friendship.getFriendId());

        friendshipStorage.removeFriendship(friendship);

        return friendship;
    }

    public Collection<User> getFriendsByUserId(Long userId) {
        userService.getUserById(userId);

        List<Long> friendsIds = friendshipStorage.getFriendIdsForUserById(userId);

        List<User> response = userService.getUsersByIDs(friendsIds);

        return response;
    }

    public Collection<User> getCommonUserFriendsByUserId(long userId, long friendId) {
        List<Long> friendsForUserIds = friendshipStorage.getFriendIdsForUserById(userId);
        List<Long> friendsForFriendIds = friendshipStorage.getFriendIdsForUserById(friendId);

        List<Long> userFriendsCopy = new ArrayList<>(friendsForUserIds);

        userFriendsCopy.retainAll(friendsForFriendIds);

        List<User> response = userService.getUsersByIDs(userFriendsCopy);

        return response;
    }
}
