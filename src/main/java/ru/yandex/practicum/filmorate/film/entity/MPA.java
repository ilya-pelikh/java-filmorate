package ru.yandex.practicum.filmorate.film.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class MPA {
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MPA)) {
            return false;
        }
        MPA mPA = (MPA) o;
        return Objects.equals(name, mPA.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
