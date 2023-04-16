package ru.yandex.practicum.filmorate.storage.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> getFilms() {
        String sqlQuery = "SELECT * " +
                "FROM films " +
                "INNER JOIN mpa_rating ON films.mpa_rating_id = mpa_rating.mpa_rating_id ";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilm(Long id) {
        String sqlQuery = "SELECT * " +
                "FROM films " +
                "INNER JOIN mpa_rating ON films.mpa_rating_id = mpa_rating.mpa_rating_id " +
                "WHERE films.film_id = ? ";
        Film film = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), id);
        film.setLikes(new HashSet<>(getLikes(id)));
        return film;
    }

    @Override
    public Film save(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id")
                .usingColumns("name", "description", "release_date", "duration", "mpa_rating_id");

        Long filmId = simpleJdbcInsert.executeAndReturnKey(filmToMap(film)).longValue();
        film.setId(filmId);

        createFilmGenre(film);
        return getFilm(film.getId());
    }

    private void createFilmGenre(Film film) {
        if (film.getGenres() == null) return;
        Set<Integer> genresId = new HashSet<>();
        for (Genre genre : film.getGenres()) {
            genresId.add(genre.getId());
        }

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film_genre")
                .usingColumns("film_id", "genre_id");
        for (Integer id : genresId) {
            simpleJdbcInsert.execute(Map.of("film_id", film.getId().toString(), "genre_id", id));
        }
    }

    private void deleteFilmGenre(Long id) {
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_genre.film_id = ?", id);
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update("UPDATE films " +
                        "SET films.name = ?, films.description = ?, films.release_date = ?, films.duration = ?, films.mpa_rating_id = ? " +
                        "WHERE films.film_id = ? ",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        deleteFilmGenre(film.getId());
        createFilmGenre(film);

        return getFilm(film.getId());
    }

    public Film likeFilm(Long filmId, Long userId) {
        if (!getLikes(filmId).contains(userId)) {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("film_like")
                    .usingColumns("film_id", "user_id");

            simpleJdbcInsert.execute(
                    Map.of("film_id", filmId,
                            "user_id", userId
                    )
            );
        }
        return  getFilm(filmId);
    }


    @Override
    public Film deleteLike(Long filmId, Long userId) {
        if (getLikes(filmId).contains(userId)) {
            String sql =
                    "DELETE FROM film_like WHERE user_id = ? AND film_id = ?";

            jdbcTemplate.update(sql, userId, filmId);
        }
        return getFilm(filmId);
    }

    @Override
    public Collection<Film>  getTopFilms (Integer count) {
        return jdbcTemplate.query("SELECT * " +
                "FROM films" +
                "    LEFT JOIN (SELECT film_id, count(film_like.film_id) AS count " +
                "      FROM film_like "  +
                "      GROUP BY film_like.film_id) AS top ON top.film_id = films.film_id " +
                "         INNER JOIN mpa_rating ON films.mpa_rating_id = mpa_rating.mpa_rating_id " +
                "ORDER BY count DESC " +
                "LIMIT ?;"
                , (rs, rowNum) -> makeFilm(rs), count);
    }

    @Override
    public Collection<Integer> getGenres(Long id) {
        return jdbcTemplate.query(
                "SELECT film_genre.genre_id FROM film_genre WHERE film_genre.film_id = ?"
                , (rs, rowNum) -> rs.getInt("genre_id"), id);
    }

    @Override
    public List<Long> getLikes(Long id) {
        return jdbcTemplate.query(
                "SELECT film_like.user_id FROM film_like WHERE film_like.film_id = ?"
                , (rs, rowNum) -> rs.getLong("user_id"), id);
    }

    private Map<String, Object> filmToMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("mpa_rating_id", film.getMpa().getId());
        return values;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("films.film_id");
        String name = rs.getString("films.name");
        String description = rs.getString("films.description");
        LocalDate releaseDate = rs.getDate("films.release_date").toLocalDate();
        Integer duration = rs.getInt("films.duration");
        Integer mpaRatingId = rs.getInt("films.mpa_rating_id");
        String mpaRatingName = rs.getString("mpa_rating.mpa_rating_name");

        return Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .likes(new HashSet<>(getLikes(id)))
                .mpa(new Mpa(mpaRatingId, mpaRatingName))
                .build();
    }
}