package ru.yandex.practicum.filmorate.DbStorageTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaStorage;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MpaDbStorageTest {
    private final MpaStorage ratingService;

    @Test
    public void getGenreTest() {
        assertThat(ratingService.getMpa(1)).isNotNull();
        assertThat(ratingService.getMpa(1).getId()).isNotNull();
        assertThat(ratingService.getMpa(1).getId()).isPositive();
        assertThat(ratingService.getMpa(1).getName()).isNotNull();
        assertThat(ratingService.getMpa(1).getName()).isNotBlank();
    }

    @Test
    public void getMpaTest() {
        assertThat(ratingService.getAll()).isNotNull();
        ArrayList<Mpa> mpas = (ArrayList<Mpa>) ratingService.getAll();
        for (Mpa mpa : mpas) {
            assertThat(mpa.getId()).isNotNull();
            assertThat(mpa.getId()).isPositive();
            assertThat(mpa.getName()).isNotNull();
            assertThat(mpa.getName()).isNotBlank();
        }

    }

}