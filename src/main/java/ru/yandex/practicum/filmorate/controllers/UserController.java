package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@Component
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping()
    public User post(@RequestBody @Valid User user) throws IdException {
        userService.save(user);
        return user;
    }

    @PutMapping()
    public User put(@RequestBody @Valid User user) throws UserNotFoundException {
        userService.update(user);
        return user;
    }

    @GetMapping()
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public ArrayList<User> getFriendsList(@PathVariable int id) throws UserNotFoundException {
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> getMutualFriendsList(@PathVariable int id, @PathVariable int otherId) throws UserNotFoundException {
        return userService.getMutualFriendsList(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) throws UserNotFoundException {
        System.out.println(id);
        return userService.getUser(id);
    }
}
