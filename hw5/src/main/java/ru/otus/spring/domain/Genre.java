package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Genre {

    private long id;

    private String name;

    public Genre(String name) {
        this.id = 1;
        this.name = name;
    }
}
