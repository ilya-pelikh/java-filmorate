package ru.yandex.practicum.filmorate.user.mapper;

import ru.yandex.practicum.filmorate.user.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.user.entity.User;

public class UserMapper {
    public static User toEntity(UserRequestDto dto) {
        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getLogin(),
                dto.getName(),
                dto.getBirthday());

    }

    public static User toResponseDto(User user) {
        return new User(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
    }
}