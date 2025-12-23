package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        User addedUser = userService.addUser(user);

        log.trace("Добавление пользователя{}->{}", addedUser.getId(), user);

        return user;
    }

    @PutMapping
    public User updateUser(@Validated(ValidationGroups.OnUpdate.class) @RequestBody User user) {
        User editedUser = userService.editUser(user.getId(), user);

        log.trace("Обновление пользователя{}->{}", editedUser.getId(), user);

        return user;
    }

    @PutMapping(path = "/{userId}/friends/{friendId}")
    public Friendship addLikeToFilmByUser(@PathVariable long userId, @PathVariable long friendId) {
        Friendship friendship = friendshipService.addUserToFriends(userId, friendId);

        log.trace("Добавление лайка {}->{}", userId, friendship.getId());

        return friendship;
    }

    @DeleteMapping(path = "/{userId}/friends/{friendId}")
    public Friendship removeLikeFromFilmByUser(@PathVariable long userId, @PathVariable long friendId) {
        Friendship friendship = friendshipService.removeUserFromFriends(userId, friendId);

        log.trace("Удаление лайка {}->{}", userId, friendship.getId());

        return friendship;
    }

    @GetMapping(path = "/{userId}/friends")
    public Collection<User> getUserFriendsByUserId(@PathVariable long userId) {
        return friendshipService.getFriendsByUserId(userId);
    }

    @GetMapping(path = "{userId}/friends/common/{friendId}")
    public Collection<User> getCommonUserFriendsByUserId(@PathVariable long userId, @PathVariable long friendId) {
        return friendshipService.getCommonUserFriendsByUserId(userId, friendId);
    }
}
