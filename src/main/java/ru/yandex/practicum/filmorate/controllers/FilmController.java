package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@Component
@RestController
@RequestMapping("films")
public class FilmController {
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private final FilmService filmService;

    @PostMapping()
    public Film post(@RequestBody @Valid Film film) throws IdException, FilmDateException {
        filmService.saveFilm(film);
        return film;
    }

    @PutMapping()
    public Film put(@RequestBody @Valid Film film) throws FilmNotFoundException {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) throws FilmNotFoundException {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) throws IdException, FilmNotFoundException, UserNotFoundException {
        System.out.println(id + " " + userId);
        filmService.addLike(id, userId);
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) int count) throws NegativeNumberException {
        return filmService.getTopFilms(count);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) throws UserNotFoundException, FilmNotFoundException, IdException {
        System.out.println(id + " " + userId);
        return filmService.deleteLike(id, userId);
    }

}

