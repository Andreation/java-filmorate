package ru.yandex.practicum.filmorate.DbStorageTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenreDbStorageTest {

    private final GenreStorage genreStorage;

    @Test
    public void getGenreTest() {
        assertThat(genreStorage.getGenre(1)).isNotNull();
        assertThat(genreStorage.getGenre(1).getId()).isPositive();
        assertThat(genreStorage.getGenre(1).getName()).isNotBlank();

    }

    @Test
    public void getMpaTest() {
        assertThat(genreStorage.getGenres()).isNotNull();
        ArrayList<Genre> genres = (ArrayList<Genre>) genreStorage.getGenres();
        for (Genre genre : genres) {
            assertThat(genre.getId()).isNotNull();
            assertThat(genre.getId()).isPositive();
            assertThat(genre.getName()).isNotBlank();
        }
    }

}