package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {

    private long id;

    private String name;

    private Author author;

    private Genre genre;
}
