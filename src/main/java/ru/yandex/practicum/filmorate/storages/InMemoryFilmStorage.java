package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
@Component
public class InMemoryFilmStorage implements FilmStorage {
    Map<Integer, Film> films = new LinkedHashMap<>();
    protected Integer id = 0;

    public Film save(Film film) throws IdException, FilmDateException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,2, 27))){
            throw new FilmDateException("Не верно введена дата");
        } else if ((!films.containsKey(film.getId()))) {
            film.setId(++id);
            films.put(film.getId(), film);
            return film;
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public Film update(Film film) throws IdException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    @Override
    public Film deleteFilm(int id) {
        return films.remove(id);
    }


    public Film getFilm(int id) throws IdException {
        if (films.containsKey(id)){
            return films.get(id);
        } else {
            throw new IdException("das");
        }

    }

    public ArrayList<Film> getFilms() {
        System.out.println(films.toString());
        return new ArrayList<>(films.values());
    }



    public Film likeFilm(int id, long userId) throws IdException {
        if (films.containsKey(id)){
            films.get(id).getLikesList().add(userId);
            return films.get(id);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public Film deleteLike(int id, long userId) throws IdException {
        if (films.containsKey(id) && films.get(id).getLikesList().contains(userId)){
            films.get(id).getLikesList().remove(userId);
            return films.get(id);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public ArrayList<Film> getTopFilms(int count){
        return new ArrayList<>(films.values());
    }

}