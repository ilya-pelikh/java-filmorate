package ru.yandex.practicum.filmorate.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.common.exception.ValidationException;
import ru.yandex.practicum.filmorate.user.domain.User;
import ru.yandex.practicum.filmorate.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userStorage;

    private void processUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
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
        processUser(user);

        Long addedUserId = userStorage.add(user);

        return userStorage.getById(addedUserId);
    }

    public User editUser(Long userId, User user) throws ValidationException {
        processUser(user);

        if (!userStorage.checkUserForExistance(userId)) {
            throw new NotFoundException(String.format("Пользователя с id=%s не сущесуствует", userId));
        }

        processUser(user);

        Long editedUserId = userStorage.edit(userId, user);

        return userStorage.getById(editedUserId);
    }

    public boolean checkUserForExistance(Long userId) {
        return userStorage.checkUserForExistance(userId);
    }
}
