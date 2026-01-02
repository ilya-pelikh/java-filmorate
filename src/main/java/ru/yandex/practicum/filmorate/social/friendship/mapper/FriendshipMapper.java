package ru.yandex.practicum.filmorate.social.friendship.mapper;

import ru.yandex.practicum.filmorate.social.friendship.domain.Friendship;
import ru.yandex.practicum.filmorate.social.friendship.dto.FriendshipRequestDto;
import ru.yandex.practicum.filmorate.social.friendship.dto.FriendshipResponseDto;

public class FriendshipMapper {
    public static Friendship toDomain(FriendshipRequestDto dto) {
        return Friendship.builder()
                .userId(dto.getUserId())
                .friendId(dto.getFriendId())
                .build();
    }

    public static FriendshipResponseDto toResponse(Friendship friendship) {
        return FriendshipResponseDto.builder()
                .userId(friendship.getUserId())
                .friendId(friendship.getFriendId())
                .build();
    }
}
