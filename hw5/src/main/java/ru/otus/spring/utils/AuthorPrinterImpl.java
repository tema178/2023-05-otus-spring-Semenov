package ru.otus.spring.utils;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.OutputService;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class AuthorPrinterImpl implements AuthorPrinter {

    private final OutputService outputService;

    public AuthorPrinterImpl(OutputService outputService) {
        this.outputService = outputService;
    }

    @Override
    public void print(Author author) {
        String format = String.format("id: %s, name: %s",
                author.getId(), author.getName());
        outputService.outputString(format);
    }

    @Override
    public void print(String prefix, Author author) {
        outputService.outputString(prefix);
        print(author);
    }

    @Override
    public void print(List<Author> authors) {
        authors.sort((b1, b2) -> b1.getId() > b2.getId() ? 1 : -1);
        for (var author: authors) {
            print(author);
        }
    }
}
