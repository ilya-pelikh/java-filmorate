package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    User user;

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setEmail("boris@yandex.ru");
        user.setLogin("boris");
        user.setName("Борис");
        user.setBirthday(LocalDate.of(1995, 1, 1));
    }

    @Test
    void validate_ShouldNotThrowValidUser() {
        assertDoesNotThrow(() -> UserController.validate(user));
    }

    @Test
    void validate_ShouldNotThrowIfNameNull() {
        user.setName(null);

        assertDoesNotThrow(() -> UserController.validate(user));
    }

    @Test
    void validate_ShouldNotThrowIfNameBlank() {
        user.setName("   ");

        assertDoesNotThrow(() -> UserController.validate(user));
    }

    @Test
    void validate_ShouldNotThrowIfBirthDayIsToday() {
        user.setBirthday(LocalDate.now());

        assertDoesNotThrow(() -> UserController.validate(user));
    }

    @Test
    void validate_ShouldThrowIfEmailIsBlank() {
        user.setEmail("");

        assertThrows(ValidationException.class, () -> UserController.validate(user));
    }

    @Test
    void validate_ShouldThrowIfEmailDoesNotContainSobaka() {
        user.setEmail("boris.yandex.ru");

        assertThrows(ValidationException.class, () -> UserController.validate(user));
    }

    @Test
    void validate_ShouldThrowIfLoginIsBlank() {
        user.setLogin("");

        assertThrows(ValidationException.class, () -> UserController.validate(user));
    }

    @Test
    void validate_ShouldThrowIfBirthDayIsTomorrow() {

        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> UserController.validate(user));
    }
}
