package ru.yandex.practicum.filmorate.validate;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Component
public class ValidateService {
    public void validateFilmDate(Film film) throws FilmDateException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,2, 27))) {
            throw new FilmDateException("Ошибка даты");
        }
    }

}
