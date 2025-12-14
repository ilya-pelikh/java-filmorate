package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    public static void validate(User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }

        if (user.getLogin().isBlank()) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

    @GetMapping
    public Collection<User> get() {
        return users.values();
    }

    @PostMapping
    public User add(@RequestBody User user) {
        try {
            validate(user);
        } catch (ValidationException exception) {
            log.info("{}{}", exception.getMessage(), user);
            throw exception;
        }

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        long id = getNextId();
        user.setId(id);
        users.put(id, user);
        log.trace("Добавление пользователя{}->{}", users.get(id), user);

        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        try {
            validate(user);
        } catch (ValidationException exception) {
            log.info("{}{}", exception.getMessage(), user);
            throw exception;
        }

        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с данный id не сущесуствует");
        }

        if (user.getName() == null) {
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
