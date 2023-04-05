package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserStorage {
    Map<Integer, User> users = new LinkedHashMap<>();
    protected Integer id = 0;

    public void save(User user) throws IdException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!users.containsKey(user.getId())) {
            user.setId(++id);
            users.put(user.getId(), user);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public void update(User user) throws IdException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else {
                throw new IdException("Не верно введен id");
            }
    }

    public ArrayList<User> getUsers() {
        System.out.println(users.toString());
        return new ArrayList<>(users.values());
    }

}
