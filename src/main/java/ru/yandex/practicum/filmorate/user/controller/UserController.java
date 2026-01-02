package ru.yandex.practicum.filmorate.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.user.domain.User;
import ru.yandex.practicum.filmorate.user.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.user.mapper.UserMapper;
import ru.yandex.practicum.filmorate.user.service.UserService;
import ru.yandex.practicum.filmorate.common.validation.ValidationGroups;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable long id) {
        log.info("-> Получение пользователя по id: {}", id);

        User response = userService.getUserById(id);

        log.info("Получен пользователь по id: {}", response);

        return response;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody UserRequestDto dto) {
        log.info("-> Добавление пользователя: {}", dto);

        User user = UserMapper.toDomain(dto);
        User response = userService.addUser(user);

        log.info("Добавлен пользователь: {}", response);

        return response;
    }

    @PutMapping
    public User updateUser(@Validated(ValidationGroups.OnUpdate.class) @RequestBody UserRequestDto dto) {
        log.info("-> Обновление пользователя: {}", dto);

        User user = UserMapper.toDomain(dto);
        User response = userService.editUser(user.getId(), user);

        log.info("-> Обновлен пользователь {}", response);

        return response;
    }

}
