package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Film {
    Long id;

    @NotNull @NotBlank
    String name;

    @Size(max = 200)
    String description;

    @NotNull
    LocalDate releaseDate;

    @Positive
    Integer duration;

    Set<Long> likes = new HashSet<>();
    Mpa mpa;
    Set<Genre> genres = new LinkedHashSet<>();
}
