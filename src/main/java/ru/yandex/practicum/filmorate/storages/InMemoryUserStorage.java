package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> users = new LinkedHashMap<>();
    protected Long id = Long.valueOf(0);

    public User save(User user) throws IdException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!users.containsKey(user.getId())) {
            user.setId(++id);
            users.put(user.getId(), user);
            return user;
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public User update(User user) throws IdException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                return user;
            } else {
                throw new IdException("Не верно введен id");
            }
    }

    public ArrayList<User> getUsers() {
        System.out.println(users.toString());
        return new ArrayList<>(users.values());
    }

    public User addFriend(long id, long friendId) throws IdException {
        if (users.containsKey(id) && users.containsKey(friendId)) {
            users.get(id).getFriendsList().add(friendId);
            users.get(friendId).getFriendsList().add(id);
            return users.get(id);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public User deleteFriend(long id, long friendId) throws IdException {
        if (users.containsKey(id) && users.containsKey(friendId)) {
            users.get(id).getFriendsList().remove(friendId);
            users.get(friendId).getFriendsList().remove(id);
            return users.get(id);
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public ArrayList<User> getFriendsList(long id) throws IdException {
        ArrayList<User> friendList = new ArrayList<>();
        if (users.containsKey(id)) {
            for (long idFriend : users.get(id).getFriendsList()) {
                friendList.add(users.get(idFriend));
            }
            return friendList;
        } else {
            throw new IdException("Не верно введен id");
        }
    }

    public ArrayList<User> getMutualFriendsList(long id, long otherId) throws IdException {
        ArrayList<User> mutualFriends = new ArrayList<>();
        if (users.containsKey(id) && users.containsKey(otherId)) {
            for (long friendId: users.get(id).getFriendsList()) {
                if (users.get(otherId).getFriendsList().contains(friendId)) {
                    mutualFriends.add(users.get(friendId));
                }
            }
        } else {
            throw new IdException("Не верно введен id");
        }
        return mutualFriends;
    }

    public User getUser(long id) {
        return users.get(id);
    }





}
