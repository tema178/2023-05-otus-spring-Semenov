package ru.otus.example.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentMongo {

    @Id
    private String id;

    private String text;

    private String bookId;
}