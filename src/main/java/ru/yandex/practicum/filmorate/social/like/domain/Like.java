package ru.yandex.practicum.filmorate.social.like.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Like {
    private Long userId;
    private Long filmId;
}
