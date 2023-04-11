package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.NegativeNumberException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    Map<Integer, Film> films = new LinkedHashMap<>();
    protected Integer id = 0;

    public Film save(Film film) throws IdException, FilmDateException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,2, 27))) {
            throw new FilmDateException("Не верно введена дата");
        } else if ((!films.containsKey(film.getId()))) {
            film.setId(++id);
            films.put(film.getId(), film);
            return film;
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public Film update(Film film) throws FilmNotFoundException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new FilmNotFoundException("film no found");
        }
    }

    @Override
    public Film deleteFilm(int id) {
        return films.remove(id);
    }


    public Film getFilm(int id) throws FilmNotFoundException {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException("film no found");
        }

    }

    public ArrayList<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    public Film likeFilm(int id, long userId) throws FilmNotFoundException {
        if (films.containsKey(id)) {
            films.get(id).getLikesList().add(userId);
            return films.get(id);
        } else {
            throw new FilmNotFoundException("film no found");
        }
    }

    public Film deleteLike(int id, long userId) throws FilmNotFoundException {
        if (films.containsKey(id) && films.get(id).getLikesList().contains(userId)) {
            films.get(id).getLikesList().remove(userId);
            return films.get(id);
        } else {
            throw new FilmNotFoundException("film no found");
        }
    }

    public ArrayList<Film> getTopFilms(int count) throws NegativeNumberException {
        if (count > 0) {
            return (ArrayList<Film>) new ArrayList<>(films.values()).stream()
                    .sorted((o1, o2) -> {
                        if (o1.getLikesList().size() == o2.getLikesList().size())
                            return o1.getName().compareTo(o2.getName());
                        else if (o1.getLikesList().size() < o2.getLikesList().size())
                            return 1;
                        else return -1;
                    })
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            throw new NegativeNumberException("negativeNumberException");
        }
    }

}