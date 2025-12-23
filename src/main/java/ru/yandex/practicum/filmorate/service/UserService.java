package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    private void processUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    public User getUserById(long id) throws NotFoundException {
        User foundUser = userStorage.getById(id);
        if (foundUser == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", id));
        }

        return foundUser;
    }

    public User addUser(User user) {
        processUser(user);

        return userStorage.add(user);
    }

    public User editUser(long id, User user) throws ValidationException {
        if (userStorage.getById(id) == null) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", id));
        }

        processUser(user);

        return userStorage.edit(id, user);
    }
}
