package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.validate.ValidateService;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("films")
public class FilmController {
    @Autowired
    ValidateService validateService;
    protected FilmStorage filmStorage = new FilmStorage();

    @PostMapping()
    public Film post(@RequestBody @Valid Film film) throws IdException, FilmDateException {
        validateService.validateFilmDate(film);
        filmStorage.save(film);
        return film;
    }
    @PutMapping()
    public Film put(@RequestBody @Valid Film film) throws IdException{
        filmStorage.update(film);
        return film;
    }
    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmStorage.getFilms();
    }

}

