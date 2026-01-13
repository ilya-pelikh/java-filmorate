package ru.yandex.practicum.filmorate.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.common.exception.ValidationException;
import ru.yandex.practicum.filmorate.user.entity.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userStorage;

    private User processUser(User user) {
        String userName = user.getName();
        if (user.getName() == null || user.getName().isBlank()) {
            userName = user.getLogin();
        }
        return new User(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                userName,
                user.getBirthday());
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    public List<User> getUsersByIDs(List<Long> id) throws NotFoundException {
        return userStorage.getUsersByIDs(id);
    }

    public User getUserById(Long id) throws NotFoundException {
        return userStorage.getById(id);
    }

    public User addUser(User user) {
        user = processUser(user);

        return userStorage.addUser(user);

    }

    public User editUser(Long userId, User user) throws ValidationException {
        user = processUser(user);

        if (!userStorage.checkUserForExistance(userId)) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

        return userStorage.editUser(userId, user);
    }

    public boolean checkUserForExistance(Long userId) {
        return userStorage.checkUserForExistance(userId);
    }
}
