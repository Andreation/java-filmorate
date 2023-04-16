package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getFilms();

    Film getFilm(Long id);

    Film save(Film film);

    Film update(Film film);

    Film likeFilm(Long filmId, Long userId);

    Film deleteLike(Long filmId, Long userId);

    Collection<Film> getTopFilms(Integer count);

    Collection<Integer> getGenres(Long id);

    Collection<Long> getLikes(Long id);
}
