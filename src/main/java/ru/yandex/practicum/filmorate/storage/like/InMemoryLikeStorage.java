package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class InMemoryLikeStorage implements LikeStorage {
    private final Set<Like> likes = new HashSet<>();

    @Override
    public boolean contains(Like like) {
        return likes.contains(like);
    }

    @Override
    public Like add(Like like) {
        like.setId(likes.size() + 1);

        likes.add(like);

        return like;
    }

    @Override
    public Like remove(Like like) {
        likes.remove(like);

        return like;
    }

    @Override
    public Collection<Like> getAll() {
        return likes;
    }

}
