CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_rating_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_rating_name varchar(100) NOT NULL,
    mpa_description varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    film_id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          varchar(100)   NOT NULL,
    description   varchar(100)   NOT NULL,
    release_date  date      NOT NULL,
    duration      INTEGER   NOT NULL,
    mpa_rating_id INTEGER REFERENCES mpa_rating (mpa_rating_id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  INTEGER REFERENCES films (film_id),
    genre_id INTEGER REFERENCES genres (genre_id),
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email      varchar(100)   NOT NULL,
    login      varchar(100)   NOT NULL,
    name       varchar(100)   NOT NULL,
    birthday   date      NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship_status
(
    status_id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    friendship_name   varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id              INTEGER REFERENCES users (user_id),
    friend_id            INTEGER REFERENCES users (user_id),
    friendship_status_id INTEGER REFERENCES friendship_status (status_id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS film_like
(
    film_id    INTEGER REFERENCES films (film_id),
    user_id    INTEGER REFERENCES users (user_id),
    PRIMARY KEY (film_id, user_id)
);


