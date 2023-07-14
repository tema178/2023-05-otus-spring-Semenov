package ru.otus.spring.utils;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookPrinter {
    void print(Book book);

    void print(String prefix, Book book);

    void print(List<Book> books);
}
