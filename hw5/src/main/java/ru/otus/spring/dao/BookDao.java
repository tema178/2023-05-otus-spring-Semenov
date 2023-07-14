package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {
    Book create(Book book);

    Book getById(long id);

    List<Book> getAll();

    int update(Book book);

    int deleteById(long id);
}
