package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с книгами должен")
@JdbcTest
@Import({BookServiceImpl.class})
class BookServiceImplTest {

    private static final int TEST_BOOK_ID = 5;

    private static final String TEST_BOOK_NAME = "Test book";

    private static final int IVAN_ID = 1;

    private static final Author AUTHOR_IVAN = new Author(IVAN_ID, null);

    private static final Author AUTHOR_IVAN_FULL = new Author(IVAN_ID, "Ivan");

    private static final int ACTION_GENRE_ID = 1;

    private static final Genre ACTION_GENRE = new Genre(ACTION_GENRE_ID, null);

    private static final Book BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, AUTHOR_IVAN, ACTION_GENRE);

    private static final Genre ACTION_GENRE_FULL = new Genre(ACTION_GENRE_ID, "Action");

    private static final Book BOOK_FULL = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, AUTHOR_IVAN_FULL, ACTION_GENRE_FULL);

    private static final int NIKOLAY_ID = 2;

    private static final Author AUTHOR_NIKOLAY = new Author(NIKOLAY_ID, null);

    private static final String TEST_BOOK_NAME_NEW = "Test book new";

    private static final Author AUTHOR_NIKOLAY_FULL = new Author(NIKOLAY_ID, "Nikolay");

    private static final Genre CRIME_GENRE = new Genre(4, null);

    private static final int FIRST_BOOK_ID = 1;

    private static final String FIRST_BOOK_NAME = "Book of Ivan";

    private static final Book FIRST_BOOK_FULL = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
            AUTHOR_IVAN_FULL, ACTION_GENRE_FULL);

    private static final Book FIRST_BOOK = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME,
            AUTHOR_IVAN, ACTION_GENRE);


    private static final int SECOND_BOOK_ID = 2;

    private static final String SECOND_BOOK_NAME = "Book of Nikolay";

    private static final Book SECOND_BOOK_FULL = new Book(SECOND_BOOK_ID, SECOND_BOOK_NAME,
            AUTHOR_NIKOLAY_FULL, ACTION_GENRE_FULL);

    private static final Book SECOND_BOOK = new Book(SECOND_BOOK_ID, SECOND_BOOK_NAME,
            AUTHOR_NIKOLAY, ACTION_GENRE);

    private static final int EXPECTED_LIBRARY_SIZE = 2;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @DisplayName("Создать новую книгу")
    @Test
    void shouldInsertNewBook() {
        given(authorDao.getById(1)).willReturn(AUTHOR_IVAN_FULL);
        given(genreDao.getById(1)).willReturn(ACTION_GENRE_FULL);
        given(bookDao.create(BOOK)).willReturn(BOOK_FULL);
        Book result = bookService.create(BOOK);
        assertThat(result.getName()).isEqualTo(BOOK.getName());
        assertThat(result.getAuthor()).isEqualTo(BOOK_FULL.getAuthor());
        assertThat(result.getGenre()).isEqualTo(BOOK_FULL.getGenre());
    }

    @DisplayName("Получить книгу по id")
    @Test
    void shouldGetBookById() {
        given(authorDao.getById(IVAN_ID)).willReturn(AUTHOR_IVAN_FULL);
        given(genreDao.getById(ACTION_GENRE_ID)).willReturn(ACTION_GENRE_FULL);
        given(bookDao.getById(FIRST_BOOK_ID)).willReturn(FIRST_BOOK_FULL);
        Book book = bookService.getById(FIRST_BOOK_ID);
        assertThat(book.getName()).isEqualTo(FIRST_BOOK_FULL.getName());
        assertThat(book.getAuthor()).isEqualTo(FIRST_BOOK_FULL.getAuthor());
        assertThat(book.getGenre()).isEqualTo(FIRST_BOOK_FULL.getGenre());
    }

    @DisplayName("Получить все книги")
    @Test
    void shouldGetBooks() {
        given(authorDao.getById(IVAN_ID)).willReturn(AUTHOR_IVAN_FULL);
        given(authorDao.getById(NIKOLAY_ID)).willReturn(AUTHOR_NIKOLAY_FULL);
        given(genreDao.getById(ACTION_GENRE_ID)).willReturn(ACTION_GENRE_FULL);
        given(bookDao.getAll()).willReturn(List.of(FIRST_BOOK, SECOND_BOOK));
        List<Book> book = bookService.getAll();
        assertThat(book).hasSize(EXPECTED_LIBRARY_SIZE).isEqualTo(List.of(FIRST_BOOK_FULL, SECOND_BOOK_FULL));
    }

    @DisplayName("Обновить книгу по id")
    @Test
    void shouldUpdateBookById() {
        given(bookDao.update(any())).willReturn(1);
        boolean updated = bookService.update(new Book(FIRST_BOOK_ID, TEST_BOOK_NAME_NEW, AUTHOR_NIKOLAY, CRIME_GENRE));
        assertTrue(updated);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldDeleteBookById() {
        given(bookDao.deleteById(FIRST_BOOK_ID)).willReturn(1);
        boolean updated = bookService.deleteById(FIRST_BOOK_ID);
        assertTrue(updated);
    }
}
