package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_UPDATE_COUNT = 1;

    private static final int EXPECTED_GENRES_TABLE_SIZE = 6;

    private static final int EXPECTED_DELETE_COUNT = 1;

    private static final String HISTORICAL_GENRE = "Historical";

    private static final String ACTION_GENRE = "Action";

    private static final long ACTION_ID = 1;

    private static final long HORROR_ID = 6;

    private static final String ACTION_GENRE_NEW = "Action_New";

    private static final int HISTORICAL_ID = 7;

    @Autowired
    private GenreDao genreDao;

    @DisplayName("Создать новый жанр")
    @Test
    void shouldInsertNewGenre() {
        genreDao.create(new Genre(HISTORICAL_ID, HISTORICAL_GENRE));
        Genre genre = genreDao.getById(HISTORICAL_ID);
        assertThat(genre.getName()).isEqualTo(HISTORICAL_GENRE);
    }

    @DisplayName("Получить жанр по id")
    @Test
    void shouldGetGenreById() {
        Genre genre = genreDao.getById(ACTION_ID);
        assertThat(genre.getName()).isEqualTo(ACTION_GENRE);
    }

    @DisplayName("Получить все жанры")
    @Test
    void shouldGetBooks() {
        List<Genre> genre = genreDao.getAll();
        assertThat(genre).hasSize(EXPECTED_GENRES_TABLE_SIZE);
    }

    @DisplayName("Обновить жанр по id")
    @Test
    void shouldUpdateGenreById() {
        int updated = genreDao.update(new Genre(ACTION_ID, ACTION_GENRE_NEW));
        assertThat(updated).isEqualTo(EXPECTED_UPDATE_COUNT);
        Genre genre = genreDao.getById(ACTION_ID);
        assertThat(genre.getName()).isEqualTo(ACTION_GENRE_NEW);
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void shouldDeleteGenreById() {
        int updated = genreDao.deleteById(HORROR_ID);
        assertThat(updated).isEqualTo(EXPECTED_DELETE_COUNT);
        assertThrows(EmptyResultDataAccessException.class, () -> genreDao.getById(HORROR_ID));

    }

}