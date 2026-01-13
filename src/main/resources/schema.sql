CREATE TABLE IF NOT EXISTS genres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT genres_name_not_empty CHECK (TRIM(name) <> '')
);

CREATE TABLE IF NOT EXISTS mpa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT mpa_name_list CHECK (name IN ('G', 'PG', 'PG-13', 'R', 'NC-17'))
);


CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE CHECK (TRIM(email) <> ''),
    login VARCHAR(50) NOT NULL UNIQUE CHECK (TRIM(login) <> ''),
    name VARCHAR(100),
    birthday DATE NOT NULL,
    CONSTRAINT users_email_check CHECK (
        email LIKE '%_@__%.__%'
    ),
    CONSTRAINT users_birthday_check CHECK (
        birthday <= CURRENT_DATE
    )
);

CREATE TABLE IF NOT EXISTS films (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK (TRIM(name) <> ''),
    description VARCHAR(200) NOT NULL CHECK (TRIM(description) <> ''),
    release_date DATE NOT NULL CHECK (release_date > DATE '1895-12-28'),
    duration INTEGER NOT NULL CHECK (duration > 0),
    mpa_id BIGINT NOT NULL,
    CONSTRAINT fk_films_mpa FOREIGN KEY (mpa_id) REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_like (
    user_id BIGINT NOT NULL,
    film_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, film_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE
);