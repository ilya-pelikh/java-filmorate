package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User getById(long id);

    User add(User user);

    User edit(long id, User user);

    Collection<User> getAll();
}
