package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.ArrayList;

@Service
@Slf4j
public class FilmService {
    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film saveFilm(Film film) throws IdException, FilmDateException {
        return filmStorage.save(film);
    }

    public Film updateFilm(Film film) throws FilmNotFoundException {
        return filmStorage.update(film);
    }

    public Film deleteFilm(int id) {
        return filmStorage.deleteFilm(id);
    }

    public ArrayList<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(int id) throws FilmNotFoundException {
        return filmStorage.getFilm(id);
    }

    public Film addLike(int filmId, int userId) throws IdException, FilmNotFoundException, UserNotFoundException {
        filmExists(filmId);
        userExists(userId);
        return filmStorage.likeFilm(filmId, userId);
    }

    public Film deleteLike(int filmId, int userId) throws IdException, FilmNotFoundException, UserNotFoundException {
        filmExists(filmId);
        userExists(userId);
        return filmStorage.deleteLike(filmId, userId);
    }

    public ArrayList<Film> getTopFilms(int count) throws NegativeNumberException {
        return filmStorage.getTopFilms(count);
    }

    public void userExists(int userId) throws UserNotFoundException {
        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("user no found");
        }
    }

    public void filmExists(int filmId) throws FilmNotFoundException {
        if (filmStorage.getFilm(filmId) == null) {
            throw new FilmNotFoundException("film no found");
        }
    }

}

