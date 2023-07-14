package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre create(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    int update(Genre genre);

    int deleteById(long id);
}
