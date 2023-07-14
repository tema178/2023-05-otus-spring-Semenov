package ru.otus.spring.utils;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenrePrinter {
    void print(Genre genre);

    void print(String prefix, Genre genre);

    void print(List<Genre> genres);
}
