package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder(toBuilder = true)
public class User {
    Long id;



    @NotBlank
    @Email
    String email;

    @NotBlank
    String login;

    String name;

    @NotNull
    @PastOrPresent
    LocalDate birthday;
}