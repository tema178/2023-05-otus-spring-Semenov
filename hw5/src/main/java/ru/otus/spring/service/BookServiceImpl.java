package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book create(Book book) {
        Book result = bookDao.create(book);
        populateReferences(result);
        return result;
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book) > 0;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = bookDao.getAll();
        books.forEach(this::populateReferences);
        return books;
    }

    @Override
    public Book getById(long id) {
        Book result = bookDao.getById(id);
        populateReferences(result);
        return result;
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id) > 0;
    }

    private void populateReferences(Book book) {
        Author author = book.getAuthor();
        if (author != null && author.getId() != 0 && author.getName() == null) {
            book.setAuthor(authorDao.getById(author.getId()));
        }

        Genre genre = book.getGenre();
        if (genre != null && genre.getId() != 0 && genre.getName() == null) {
            book.setGenre(genreDao.getById(genre.getId()));
        }

    }


}
