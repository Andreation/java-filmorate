package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    protected UserStorage userStorage = new UserStorage();
    @PostMapping()
    public User post(@RequestBody @Valid User user) throws IdException {
        userStorage.save(user);
        return user;
    }

    @PutMapping()
    public User put(@RequestBody @Valid User user) throws IdException {
        userStorage.update(user);
        return user;
    }

    @GetMapping()
    public ArrayList<User> getFilms() {
        return userStorage.getUsers();
    }

}
