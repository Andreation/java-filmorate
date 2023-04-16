package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DAO.GenreDbStorage;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Collection<Genre> getAllGenres() {
        return genreDbStorage.getGenres();
    }

    public Genre getGenreById(Integer id) {
        try {
            return genreDbStorage.getGenre(id);
        } catch (Exception e) {
            throw new GenreNotFoundException("genre not found");
        }
    }

}