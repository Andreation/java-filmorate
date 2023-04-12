package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validate.DateFilmValidation;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;

@Slf4j
@Component
@Validated
@RestController
@RequestMapping("films")
public class FilmController {
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private final FilmService filmService;

    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getTopFilms(
            @RequestParam(defaultValue = "10", required = false) @PositiveOrZero int count) {
        return filmService.getTopFilms(count);
    }

    @PostMapping()
    public Film post(@RequestBody @DateFilmValidation @Valid Film film) {
        filmService.saveFilm(film);
        return film;
    }

    @PutMapping()
    public Film put(@RequestBody @Valid Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        System.out.println(id + " " + userId);
        filmService.addLike(id, userId);
        return filmService.getFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        System.out.println(id + " " + userId);
        return filmService.deleteLike(id, userId);
    }

}

