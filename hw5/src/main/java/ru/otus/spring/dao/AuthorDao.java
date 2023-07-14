package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author create(Author author);

    Author getById(long id);

    List<Author> getAll();

    int update(Author author);

    int deleteById(long id);
}
