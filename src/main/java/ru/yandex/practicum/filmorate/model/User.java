package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.validation.annotations.AfterDate;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {
    @NotNull(groups = ValidationGroups.OnUpdate.class)
    private long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @NotNull
    @AfterDate(fromNow = true)
    private LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login, name, birthday);
    }
}
