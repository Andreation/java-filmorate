package ru.yandex.practicum.filmorate.model;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Mpa {
    Integer id;
    String name;
}