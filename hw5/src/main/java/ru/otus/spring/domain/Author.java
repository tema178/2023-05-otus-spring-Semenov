package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author {

    private long id;

    private String name;

    public Author(String name) {
        this.id = 1;
        this.name = name;
    }
}
