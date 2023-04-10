package ru.yandex.practicum.filmorate.model;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class Film {
    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    Integer id;
    @NotBlank
    String name;
    @Size(max = 200)
    String description;
    @Past
    LocalDate releaseDate;
    @Positive
    int duration;
    Set<Long> likesList = new HashSet<>();

}
