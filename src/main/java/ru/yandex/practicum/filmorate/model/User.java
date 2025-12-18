package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.validation.annotations.AfterDate;

import java.time.LocalDate;

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
}
