package ru.yandex.practicum.filmorate.social.friendship.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friendship {
    private Long userId;
    private Long friendId;
}
