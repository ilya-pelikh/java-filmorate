package ru.yandex.practicum.filmorate.social.friendship.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendshipResponseDto {
    private long userId;
    private long friendId;
}
