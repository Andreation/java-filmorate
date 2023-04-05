package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.IdException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public class FilmStorage {
    Map<Integer, Film> films = new LinkedHashMap<>();
    protected Integer id = 0;

    public void save(Film film) throws IdException {
        if (!films.containsKey(film.getId())) {
            film.setId(++id);
            films.put(film.getId(), film);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public void update(Film film) throws IdException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public ArrayList<Film> getFilms() {
        System.out.println(films.toString());
        return new ArrayList<>(films.values());
    }

}