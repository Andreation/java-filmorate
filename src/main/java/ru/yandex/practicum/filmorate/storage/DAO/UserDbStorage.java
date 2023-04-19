package ru.yandex.practicum.filmorate.storage.DAO;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> getUsers() {
        String sqlQuery = "SELECT * FROM users";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User getUser(Long id) {
            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE users.user_id = ?",
                    (rs, rowNum) -> makeUser(rs), id);
            return user;
    }

    @Override
    public User save(User user) {
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id")
                .usingColumns("email", "login", "name", "birthday");

        Long userId = simpleJdbcInsert.executeAndReturnKey(userToMap(user)).longValue();
        user.setId(userId);

        return user;
    }

    @Override
    public User delete(Long id) {
        String sql = "DELETE FROM friends WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
        return null;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users " +
                "SET users.email = ?, users.login = ?, users.name = ?, users.birthday = ? " +
                "WHERE users.user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    public User addFriend(Long userId, Long friendId) {
        if (getFriendsId(userId).contains(friendId.intValue())) return getUser(userId);
        int status = 2;
        if (getFriendsId(friendId).contains(userId.intValue())) {
            status = 1;
            String sqlQuery = "UPDATE friendship " +
                    "SET friendship_status_id  = ? " +
                    "WHERE friendship.user_id = ? AND friendship.friend_id = ? ";
            jdbcTemplate.update(sqlQuery,
                    1,
                    friendId,
                    userId
            );
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("friendship")
                .usingColumns("user_id", "friend_id", "friendship_status_id");
        simpleJdbcInsert.execute(
                Map.of("user_id", userId,
                        "friend_id", friendId,
                        "friendship_status_id", status
                )
        );
        return getUser(userId);
    }


    public Collection<User> getFriendsList(Long id) {
        String sqlQuery = "SELECT * FROM users " +
                "WHERE user_id IN (SELECT friend_id " +
                "FROM friendship " +
                "WHERE user_id = ? )";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id.intValue());
    }

    @Override
    public Collection<User> getMutualFriendsList(long id, long otherId) {
        Collection<User> mutualFriends = new ArrayList<>();
        for (int friendId: getFriendsId(id)) {
            if (getFriendsId(otherId).contains(friendId)) {
                mutualFriends.add(getUser((long) friendId));
            }
        }
        return mutualFriends;
    }

    private Collection<Integer> getFriendsId(Long id) {
        String sqlQuery = "SELECT user_id FROM users " +
                "WHERE user_id IN (SELECT friend_id " +
                "FROM friendship " +
                "WHERE user_id = ? )";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getInt("user_id"), id);
    }

    public User deleteFriend(Long userId, Long friendId) {
        if (getFriendsId(userId).contains(userId.intValue()) && getFriendsId(friendId).contains(userId.intValue())) {
            String sqlQuery = "UPDATE friendship " +
                    "SET friendship_status_id  = ? " +
                    "WHERE friendship.user_id = ? AND friendship.friend_id = ? ";
            jdbcTemplate.update(sqlQuery,
                    2,
                    friendId,
                    userId
            );
        }
        String sql =
                "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sql, userId, friendId);

        return getUser(userId);
    }


    private Map<String, Object> userToMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Long id = rs.getLong("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return User.builder()
                .id(id)
                .email(email)
                .login(login)
                .name(name)
                .birthday(birthday)
                .build();
    }

}
