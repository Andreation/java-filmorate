MERGE INTO genres (genre_id, genre_name) VALUES (1, 'Комедия');
MERGE INTO genres (genre_id, genre_name) VALUES (2, 'Драма');
MERGE INTO genres (genre_id, genre_name) VALUES (3, 'Мультфильм');
MERGE INTO genres (genre_id, genre_name) VALUES (4, 'Триллер');
MERGE INTO genres (genre_id, genre_name) VALUES (5, 'Документальный');
MERGE INTO genres (genre_id, genre_name) VALUES (6, 'Боевик');

MERGE INTO mpa_rating VALUES (1, 'G', 'All Ages Admitted');
MERGE INTO mpa_rating VALUES (2, 'PG', 'Some Material May Be Suitable for Children');
MERGE INTO mpa_rating VALUES (3, 'PG-13', 'Some Material May Be Suitable for Children Under 13');
MERGE INTO mpa_rating VALUES (4, 'R', 'Under 17 Requires Accompanying Parent or Abult Guardian');
MERGE INTO mpa_rating VALUES (5, 'NC-17', 'No One 17 and Under Admitted');

