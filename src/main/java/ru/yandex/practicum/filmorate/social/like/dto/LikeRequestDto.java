package ru.yandex.practicum.filmorate.social.like.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRequestDto {
    private Long userId;
    private Long filmId;
}
