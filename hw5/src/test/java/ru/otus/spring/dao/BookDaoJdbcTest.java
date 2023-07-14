package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    private static final int EXPECTED_UPDATE_COUNT = 1;

    private static final int EXPECTED_DELETE_COUNT = 1;

    private static final int TEST_BOOK_ID = 3;

    private static final String TEST_BOOK_NAME = "Test book";

    private static final Author AUTHOR_IVAN = new Author(1, null);

    private static final Genre ACTION_GENRE = new Genre(1, null);

    private static final Author AUTHOR_NIKOLAY = new Author(2, null);

    private static final String TEST_BOOK_NAME_NEW = "Test book new";

    private static final Genre CRIME_GENRE = new Genre(4, null);

    private static final int FIRST_BOOK_ID = 1;

    private static final String FIRST_BOOK_NAME = "Book of Ivan";

    private static final int EXPECTED_LIBRARY_SIZE = 2;

    @Autowired
    private BookDao bookDao;

    @DisplayName("Создать новую книгу")
    @Test
    void shouldInsertNewBook() {
        bookDao.create(new Book(TEST_BOOK_ID, TEST_BOOK_NAME, AUTHOR_IVAN, ACTION_GENRE));
        Book book = bookDao.getById(TEST_BOOK_ID);
        assertThat(book.getName()).isEqualTo(TEST_BOOK_NAME);
    }

    @DisplayName("Получить книгу по id")
    @Test
    void shouldGetBookById() {
        Book book = bookDao.getById(FIRST_BOOK_ID);
        assertThat(book.getName()).isEqualTo(FIRST_BOOK_NAME);
    }

    @DisplayName("Получить все книги")
    @Test
    void shouldGetBooks() {
        List<Book> book = bookDao.getAll();
        assertThat(book).hasSize(EXPECTED_LIBRARY_SIZE);
    }

    @DisplayName("Обновить книгу по id")
    @Test
    void shouldUpdateBookById() {
        int updated = bookDao.update(new Book(FIRST_BOOK_ID, TEST_BOOK_NAME_NEW, AUTHOR_NIKOLAY, CRIME_GENRE));
        assertThat(updated).isEqualTo(EXPECTED_UPDATE_COUNT);
        Book book = bookDao.getById(FIRST_BOOK_ID);
        assertThat(book.getName()).isEqualTo(TEST_BOOK_NAME_NEW);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldDeleteBookById() {
        int updated = bookDao.deleteById(FIRST_BOOK_ID);
        assertThat(updated).isEqualTo(EXPECTED_DELETE_COUNT);
        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.getById(FIRST_BOOK_ID));
    }

}