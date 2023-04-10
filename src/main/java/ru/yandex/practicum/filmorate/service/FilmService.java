package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
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

    public Film updateFilm(Film film) throws IdException {
        return filmStorage.update(film);
    }

    public Film deleteFilm(int id) {
        return filmStorage.deleteFilm(id);
    }

    public ArrayList<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(int id) throws IdException {
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

    public ArrayList<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

    public boolean userExists(int userId) throws UserNotFoundException {
        if (!userStorage.getUsers().contains(userId)) {
            throw new UserNotFoundException("Пользователь c id: " + userId + "не найден");
        }
        return true;
    }

    public boolean filmExists(int filmId) throws IdException, FilmNotFoundException {
        if (!filmStorage.getFilms().contains(filmStorage.getFilm(filmId))) {
            throw new FilmNotFoundException("Фильм c id: " + filmId + "не найден");
        }
        return true;
    }
}

