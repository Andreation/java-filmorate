package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film save(Film film) throws IdException, FilmDateException;

    Film update(Film film) throws FilmNotFoundException;

    Film getFilm(int id) throws FilmNotFoundException;

    Film deleteFilm(int id);

    ArrayList<Film> getFilms();

    Film likeFilm(int id, long userId) throws FilmNotFoundException;

    ArrayList<Film> getTopFilms(int count);

    Film deleteLike(int id, long userId) throws FilmNotFoundException;

}
