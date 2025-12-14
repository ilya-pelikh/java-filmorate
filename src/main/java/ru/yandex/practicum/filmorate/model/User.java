package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Data
public class User {
    private long id;
    private String email;
    private String login;
    @Nullable
    private String name;
    private LocalDate birthday;
}
