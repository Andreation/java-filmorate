package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    User save(User user) throws IdException;

    User update(User user) throws UserNotFoundException;

    ArrayList<User> getUsers();

    ArrayList<User> getMutualFriendsList(long id, long otherId) throws UserNotFoundException;

    User deleteFriend(long id, long friendId) throws UserNotFoundException;

    User addFriend(long id, long friendId) throws UserNotFoundException;

    ArrayList<User> getFriendsList(long id) throws UserNotFoundException;

    User getUser(long id) throws UserNotFoundException;

}
