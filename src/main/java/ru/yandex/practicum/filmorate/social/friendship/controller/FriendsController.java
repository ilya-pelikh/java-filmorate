package ru.yandex.practicum.filmorate.social.friendship.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.social.friendship.service.FriendshipService;
import ru.yandex.practicum.filmorate.user.entity.User;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FriendsController {
    private final FriendshipService friendshipService;

    @PutMapping(path = "/users/{userId}/friends/{friendId}")
    public void addFriendToUserByUser(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("-> Добавление пользователя в друзья: {} - {}", userId, friendId);

        friendshipService.addUserToFriends(userId, friendId);

        log.info("-> Пользователь добавлен в друзья {} - {}", userId, friendId);

    }

    @DeleteMapping(path = "/users/{userId}/friends/{friendId}")
    public void removeFriendFromUser(@PathVariable long userId, @PathVariable long friendId) {
        log.info("-> Удаление пользователя из друзей: {} - {}", userId, friendId);

        friendshipService.removeUserFromFriends(userId, friendId);

        log.trace("-> Пользователь удален из друзей {} - {}", userId, friendId);

    }

    @GetMapping(path = "/users/{userId}/friends")
    public Collection<User> getUserFriendsByUserId(@PathVariable long userId) {
        log.info("-> Получение друзей для пользователя: {}", userId);

        Collection<User> response = friendshipService.getFriendsByUserId(userId);

        log.info("-> Получены друзья пользователя: {}", response);

        return response;
    }

    @GetMapping(path = "/users/{userId}/friends/common/{friendId}")
    public Collection<User> getCommonUserFriendsByUserId(@PathVariable long userId,
            @PathVariable long friendId) {
        return friendshipService.getCommonUserFriendsByUserId(userId, friendId);
    }
}
