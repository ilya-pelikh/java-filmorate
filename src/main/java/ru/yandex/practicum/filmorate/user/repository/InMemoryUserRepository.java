package ru.yandex.practicum.filmorate.user.repository;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.user.domain.User;

import java.util.Collection;
import java.util.List;

@Component
public class InMemoryUserRepository implements UserRepository {

    @Override
    public User getById(long id) {

        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Long add(User user) {

        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public Long edit(long id, User user) {

        throw new UnsupportedOperationException("Unimplemented method 'edit'");
    }

    @Override
    public Collection<User> getAll() {

        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public boolean checkUserForExistance(Long userId) {

        throw new UnsupportedOperationException("Unimplemented method 'checkExistance'");
    }

    @Override
    public List<User> getUsersByIDs(List<Long> ids) {

        throw new UnsupportedOperationException("Unimplemented method 'getUserssByIDs'");
    }

}
