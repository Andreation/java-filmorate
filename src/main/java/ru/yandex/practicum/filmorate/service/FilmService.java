package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DAO.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, GenreDbStorage genreDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreDbStorage = genreDbStorage;
    }

    public Film saveFilm(Film film) {
        filmStorage.save(film);
        return getFilm(film.getId());
    }

    public Film updateFilm(Film film) {
        filmExists(film.getId());
        filmStorage.update(film);
        return getFilm(film.getId());
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long id) {
        filmExists(id);
        Film film = filmStorage.getFilm(id);
        film.setGenres(filmStorage.getGenres(id).stream().map(genreDbStorage::getGenre).collect(Collectors.toSet()));
        return film;
    }

    public Film addLike(Long filmId, Long userId) {
        filmExists(filmId);
        userExists(userId);
        filmStorage.likeFilm(filmId, userId);
        return getFilm(filmId);
    }

    public Film deleteLike(Long filmId, Long userId) {
        filmExists(filmId);
        userExists(userId);
        filmStorage.deleteLike(filmId, userId);
        return getFilm(filmId);
    }

    public Collection<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

    public void userExists(Long userId) {
        try {
            userStorage.getUser(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("user no found");
        }
    }

    public void filmExists(Long filmId) {
        try {
            filmStorage.getFilm(filmId);
        } catch (Exception e) {
            throw new FilmNotFoundException("film no found");
        }
    }

}

