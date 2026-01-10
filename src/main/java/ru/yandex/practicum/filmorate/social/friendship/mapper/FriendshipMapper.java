package ru.yandex.practicum.filmorate.social.friendship.mapper;

import ru.yandex.practicum.filmorate.social.friendship.dto.FriendshipRequestDto;
import ru.yandex.practicum.filmorate.social.friendship.dto.FriendshipResponseDto;
import ru.yandex.practicum.filmorate.social.friendship.entity.Friendship;

public class FriendshipMapper {
    public static Friendship toEntity(FriendshipRequestDto dto) {
        return new Friendship(
                dto.getUserId(),
                dto.getFriendId());
    }

    public static FriendshipResponseDto toResponse(Friendship friendship) {
        return FriendshipResponseDto.builder()
                .userId(friendship.getUserId())
                .friendId(friendship.getFriendId())
                .build();
    }
}
