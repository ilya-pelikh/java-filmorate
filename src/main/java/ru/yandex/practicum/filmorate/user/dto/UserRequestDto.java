package ru.yandex.practicum.filmorate.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.common.validation.ValidationGroups;
import ru.yandex.practicum.filmorate.common.validation.annotations.AfterDate;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = { "id" })
public class UserRequestDto {
    @NotNull(groups = ValidationGroups.OnUpdate.class)
    private Long id;

    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @NotNull
    @AfterDate(fromNow = true)
    private LocalDate birthday;
}
