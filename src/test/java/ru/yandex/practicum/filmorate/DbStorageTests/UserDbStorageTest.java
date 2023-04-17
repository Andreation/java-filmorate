package ru.yandex.practicum.filmorate.DbStorageTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDbStorageTest {
    private final UserStorage userStorage;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void beforeEach() {
        user1 = new User(
                1L,
                "test@test.com",
                "user1",
                "nameUser1",
                LocalDate.of(2004,3,3)
        );
        user2 = new User(
                2L,
                "test@test.com",
                "user2",
                "nameUser2",
                LocalDate.of(2004,3,3)
        );
        user3 = new User(
                3L,
                "test@test.com",
                "user3",
                "nameUser3",
                LocalDate.of(2004,3,3)
        );
    }

    @Test
    public void SaveUserTest() {
        User user = user1;
        userStorage.save(user1);

        assertThat(user.getId()).isEqualTo(user1.getId());
        assertThat(user.getName()).isEqualTo(user1.getName());
        assertThat(user.getEmail()).isEqualTo(user1.getEmail());
        assertThat(user.getLogin()).isEqualTo(user1.getLogin());
        assertThat(user.getName()).isEqualTo(user1.getName());
    }

    @Test
    public void getUserTest() {
        userStorage.save(user1);
        assertThat(userStorage.getUser(1L).toString()).isEqualTo(user1.toString());
    }

    @Test
    public void getUsersTest() {
        userStorage.save(user1);
        userStorage.save(user2);
        Collection<User> users = userStorage.getUsers();
        assertThat(users).hasSize(2);
    }

    @Test
    public void UpdateUserTest() {
        userStorage.save(user1);
        user1.setName("updateName");
        user1.setLogin("updateLogin");
        user1.setEmail("updateEmail@tests.com");

        userStorage.update(user1);
        User user = userStorage.getUser(1L);

        assertThat(user.getId()).isEqualTo(user1.getId());
        assertThat(user.getName()).isEqualTo(user1.getName());
        assertThat(user.getEmail()).isEqualTo(user1.getEmail());
        assertThat(user.getLogin()).isEqualTo(user1.getLogin());
    }

}