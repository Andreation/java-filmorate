package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film save(Film film);

    Film update(Film film);

    Film getFilm(int id);

    Film deleteFilm(int id);

    ArrayList<Film> getFilms();

    Film likeFilm(int id, long userId);

    ArrayList<Film> getTopFilms(int count);

    Film deleteLike(int id, long userId);

}
