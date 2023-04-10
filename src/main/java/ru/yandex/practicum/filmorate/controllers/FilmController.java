package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validate.ValidateService;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@Component
@RestController
@RequestMapping("films")
public class FilmController {
    @Autowired
    public FilmController(FilmService filmService) throws IdException {
        this.filmService = filmService;
    }
    private final FilmService filmService;

    @PostMapping()
    public Film post(@RequestBody @Valid Film film) throws IdException, FilmDateException {
        filmService.saveFilm(film);
        return film;
    }

    @PutMapping()
    public Film put(@RequestBody @Valid Film film) throws IdException {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping()
    public ArrayList<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) throws IdException, FilmNotFoundException, UserNotFoundException {
        filmService.addLike(id, userId);
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) int count){
        return filmService.getTopFilms(count);
    }





//    @PutMapping("{id}/like/{userId}")
//    public Optional<Post> findById(@PathVariable int id, @PathVariable long userId) {
//
//        return posts.stream()
//                .filter(x -> x.getId() == postId)
//                .findFirst();
//    }

}

