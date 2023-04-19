package ru.yandex.practicum.filmorate.DbStorageTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbStorageTest {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private Film film1;
    private Film film2;
    private User user1;

    @BeforeEach
    public void beforeEach() {
        film1 = new Film(
                1L,
                "film1",
                "description",
                LocalDate.of(2010, Month.MARCH, 20),
                100,
                new HashSet<>(),
                new Mpa(1, "G"),
                Collections.singleton(new Genre(1, "Комедия"))
        );
        film2 = new Film(
                2L,
                "film2",
                "description",
                LocalDate.of(2010, Month.MARCH, 20),
                100,
                new HashSet<>(),
                new Mpa(1, "G"),
                Collections.singleton(new Genre(1, "Комедия"))
        );
        user1 = new User(
                1L,
                "test@test.com",
                "user1",
                "nameUser1",
                LocalDate.of(2004,3,3)
                );
    }

    @Test
    public void saveFilmTest() {
        Film film = film1;
        filmStorage.save(film1);

        assertThat(film.getId()).isEqualTo(film1.getId());
        assertThat(film.getName()).isEqualTo(film1.getName());
        assertThat(film.getDescription()).isEqualTo(film1.getDescription());
        assertThat(film.getReleaseDate()).isEqualTo(film1.getReleaseDate());
        assertThat(film.getDuration()).isEqualTo(film1.getDuration());
        assertThat(film.getMpa()).isEqualTo(film1.getMpa());
    }

    @Test
    public void getFilmTest() {
        filmStorage.save(film1);
        assertThat(filmStorage.getFilm(1L).toString()).isEqualTo(film1.toString());
    }

    @Test
    public void getFilmsTest() {
        filmStorage.save(film1);
        filmStorage.save(film2);
        Collection<Film> films = filmStorage.getFilms();
        assertThat(films).hasSize(2);
    }

    @Test
    public void updateFilmTest() {
        filmStorage.save(film1);
        film1.setName("updateName");
        film1.setDescription("updateDescription");
        film1.setReleaseDate(LocalDate.of(2007, Month.MARCH, 11));
        film1.setDuration(555);
        film1.setMpa(new Mpa(3, "PG-13"));
        filmStorage.update(film1);
        Film film = filmStorage.getFilm(1L);

        assertThat(film.getName()).isEqualTo(film1.getName());
        assertThat(film.getDescription()).isEqualTo(film1.getDescription());
        assertThat(film.getReleaseDate()).isEqualTo(film1.getReleaseDate());
        assertThat(film.getDuration()).isEqualTo(film1.getDuration());
        assertThat(film.getMpa().toString()).isEqualTo(film1.getMpa().toString());
    }

    @Test
    public void deleteAndAddLikeTest() {
        filmStorage.save(film1);
        userStorage.save(user1);
        filmStorage.likeFilm(film1.getId(), user1.getId());
        assertThat(filmStorage.getLikes(film1.getId())).contains(user1.getId());
        filmStorage.deleteLike(film1.getId(), user1.getId());
        assertThat(filmStorage.getLikes(film1.getId())).doesNotContain(user1.getId());
    }

    @Test
    public void getTopfilms() {
        User user2 = user1;
        User user3 = user1;
        user2.setId(2L);
        user3.setId(3L);
        userStorage.save(user1);
        userStorage.save(user2);
        userStorage.save(user3);
        Film film3 = film1;
        film3.setId(3L);
        film3.setName("film3");
        filmStorage.save(film1);
        filmStorage.save(film2);
        filmStorage.save(film3);
        filmStorage.likeFilm(1L, 2L);
        filmStorage.likeFilm(1L, 3L);
        filmStorage.likeFilm(2L, 1L);
        filmStorage.likeFilm(2L, 2L);
        filmStorage.likeFilm(2L, 3L);
        filmStorage.likeFilm(3L, 2L);
        ArrayList<Film> films = (ArrayList<Film>) filmStorage.getTopFilms(3);
        assertThat(films.get(0).getId()).isEqualTo(2L);
        assertThat(films.get(1).getId()).isEqualTo(1L);
        assertThat(films.get(2).getId()).isEqualTo(3L);
    }

    @Test
    public void getGenreTest() {

    }

    @Test
    public void getGenresTest() {
        assertThat(genreStorage.getGenres()).isNotNull();
        ArrayList<Genre> genres = (ArrayList<Genre>) genreStorage.getGenres();
        for (Genre genre : genres) {
            assertThat(genre.getId()).isNotNull();
            assertThat(genre.getId()).isPositive();
            assertThat(genre.getName()).isNotBlank();
        }
    }

}