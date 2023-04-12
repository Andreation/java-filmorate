package ru.yandex.practicum.filmorate.validate;

import ru.yandex.practicum.filmorate.exceptions.FilmDateException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateFilmValidator implements ConstraintValidator<DateFilmValidation, Film>
{
    public boolean isValid(Film film, ConstraintValidatorContext cxt) {
            return film.getReleaseDate().isAfter(LocalDate.of(1895,2, 27));
    }

}
