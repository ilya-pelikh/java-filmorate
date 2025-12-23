package ru.yandex.practicum.filmorate.storage.friendship;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final Set<Friendship> friendships = new HashSet<>();

    @Override
    public boolean contains(Friendship friendship) {
        return friendships.contains(friendship);
    }

    @Override
    public Friendship add(Friendship friendship) {
        friendship.setId(friendships.size() + 1);

        friendships.add(friendship);

        return friendship;
    }

    @Override
    public Friendship remove(Friendship friendship) {
        friendships.remove(friendship);

        return friendship;
    }

    @Override
    public Collection<Friendship> getAll() {
        return friendships;
    }
}
