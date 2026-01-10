package ru.yandex.practicum.filmorate.user.repository;

import java.util.Collection;
import java.util.List;

import ru.yandex.practicum.filmorate.common.exception.NotFoundException;
import ru.yandex.practicum.filmorate.user.entity.User;

public interface UserRepository {
    Collection<User> getAll();

    User getById(long id) throws NotFoundException;

    List<User> getUsersByIDs(List<Long> ids);

    Long addUser(User user);

    Long editUser(long id, User user);

    boolean checkUserForExistance(Long userId);
}
