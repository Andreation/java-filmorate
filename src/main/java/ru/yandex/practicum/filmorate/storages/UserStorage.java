package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    User save(User user) throws IdException;

    User update(User user) throws IdException;

    ArrayList<User> getUsers();

    ArrayList<User> getMutualFriendsList(long id, long otherId) throws IdException;

    User deleteFriend(long id, long friendId) throws IdException;

    User addFriend(long id, long friendId) throws IdException;

    ArrayList<User> getFriendsList(long id) throws IdException;

    User getUser(long id);

}
