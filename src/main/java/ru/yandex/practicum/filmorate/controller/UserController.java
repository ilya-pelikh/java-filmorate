package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    private boolean getHasUserName(User user) {
        return !(user.getName() == null || user.getName().isBlank());
    }

    @GetMapping
    public Collection<User> get() {
        return users.values();
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        if (!getHasUserName(user)) {
            user.setName(user.getLogin());
        }

        long id = getNextId();
        user.setId(id);
        users.put(id, user);
        log.trace("Добавление пользователя{}->{}", users.get(id), user);

        return user;
    }

    @PutMapping
    public User update(@Validated(ValidationGroups.OnUpdate.class) @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с данный id не сущесуствует");
        }

        if (!getHasUserName(user)) {
            user.setName(user.getLogin());
        }

        long id = user.getId();

        log.trace("Обновление пользователя{}->{}", users.get(id), user);
        users.put(id, user);

        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
