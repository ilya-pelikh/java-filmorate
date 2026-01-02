package ru.yandex.practicum.filmorate.user.mapper;

import ru.yandex.practicum.filmorate.user.domain.User;
import ru.yandex.practicum.filmorate.user.dto.UserRequestDto;

public class UserMapper {
    public static User toDomain(UserRequestDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setBirthday(dto.getBirthday());

        return user;
    }

    public static User toResponseDto(User user) {
        User dto = new User();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setLogin(user.getLogin());
        dto.setName(user.getName());
        dto.setBirthday(user.getBirthday());

        return dto;
    }
}