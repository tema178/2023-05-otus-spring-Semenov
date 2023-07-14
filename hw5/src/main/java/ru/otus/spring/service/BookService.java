package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    boolean update(Book book);

    List<Book> getAll();

    Book getById(long id);

    boolean deleteById(long id);
}
