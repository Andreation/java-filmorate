package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getUsers();

    User getUser(Long id);
    Collection<User> getMutualFriendsList(long id, long otherId);

    User save(User user);

    User delete(Long id);

    User update(User user);

    User addFriend(Long userId, Long friendId);

    Collection<User> getFriendsList(Long id);

    User deleteFriend(Long userId, Long friendId);

}
