package ru.yandex.practicum.filmorate.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Genre {
    Integer id;
    String name;
}
