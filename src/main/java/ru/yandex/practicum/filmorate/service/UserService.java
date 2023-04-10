package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    private final UserStorage userStorage;

    public User save(User user) throws IdException {
        return userStorage.save(user);
    }

    public User update(User user) throws IdException {
        return userStorage.update(user);
    }

    public ArrayList<User> getUsers(){
        return userStorage.getUsers();
    }

    public User addFriend(long id, long friendId) throws IdException {

        return userStorage.addFriend(id, friendId);
    }

    public User deleteFriend(long id, long friendId) throws IdException {
        return userStorage.deleteFriend(id, friendId);
    }

    public ArrayList<User> getMutualFriendsList(long id, long otherId) throws IdException {
        return userStorage.getMutualFriendsList(id, otherId);
    }

    public ArrayList<User> getFriendsList(long id) throws IdException {
        return userStorage.getFriendsList(id);
    }

    public User getUser(long id) {
        return userStorage.getUser(id);
    }


}
