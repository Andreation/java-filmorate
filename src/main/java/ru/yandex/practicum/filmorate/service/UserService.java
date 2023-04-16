package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User save(User user) throws IdException {
        return userStorage.save(user);
    }

    public User update(User user) {
        userExists(user.getId());
        return getUser(user.getId());
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(long id, long friendId) {
        userExists(id);
        userExists(friendId);
        userStorage.addFriend(id, friendId);
        return getUser(id);
    }

    public User deleteFriend(long id, long friendId) {
        userExists(id);
        userExists(friendId);
        return getUser(id);
    }

    public Collection<User> getMutualFriendsList(long id, long otherId) {
        userExists(id);
        userExists(otherId);
        return userStorage.getMutualFriendsList(id, otherId);
    }

    public Collection<User> getFriendsList(long id) {
        userExists(id);
        return userStorage.getFriendsList(id);
    }

    public User getUser(long id) {
        userExists(id);
        return userStorage.getUser(id);
    }

    public void userExists(Long userId) {
        try {
            userStorage.getUser(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("user no found");
        }
    }

}
