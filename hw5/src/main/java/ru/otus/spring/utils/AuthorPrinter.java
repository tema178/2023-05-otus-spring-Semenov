package ru.otus.spring.utils;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorPrinter {
    void print(Author author);

    void print(String prefix, Author author);

    void print(List<Author> authors);
}
