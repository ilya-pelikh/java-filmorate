package ru.yandex.practicum.filmorate.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class User {
    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name)
                && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login, name, birthday);
    }

}
