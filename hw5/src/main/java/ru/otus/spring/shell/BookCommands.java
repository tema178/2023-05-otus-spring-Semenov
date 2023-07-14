package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.InputService;
import ru.otus.spring.utils.BookPrinter;


@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class BookCommands {


    private final BookService quizService;

    private final InputService inputService;

    private final BookPrinter bookPrinter;


    @ShellMethod(value = "Show all books", key = {"allBooks"})
    public void all() {
        bookPrinter.print(quizService.getAll());
    }

    @ShellMethod(value = "Create new book", key = {"createBook"})
    public void create() {
        String bookName = inputService.readStringWithPrompt("Enter book name:");
        long authorId = inputService.readLongWithPrompt("Enter author id:");
        long genreId = inputService.readLongWithPrompt("Enter genre id:");

        Book book = quizService.create(new Book(1, bookName, new Author(authorId, null), new Genre(genreId, null)));
        bookPrinter.print("Book has been created: " , book);
    }

    @ShellMethod(value = "Update book by id", key = {"updateBook"})
    public String update() {
        long id = inputService.readLongWithPrompt("Enter book id:");
        String bookName = inputService.readStringWithPrompt("Enter book name:");
        long authorId = inputService.readLongWithPrompt("Enter author id:");
        long genreId = inputService.readLongWithPrompt("Enter genre id:");

        Book book = new Book(id, bookName, new Author(authorId, null), new Genre(genreId, null));
        boolean update = quizService.update(book);
        return update ? "Book has been updated" : "Book hasn't been updated";
    }

    @ShellMethod(value = "Get book by id", key = {"getBook"})
    public void get(long id) {
        Book book = quizService.getById(id);
        bookPrinter.print(book);
    }

    @ShellMethod(value = "Delete book", key = {"deleteBook"})
    public String deleteBook(long id) {
        return quizService.deleteById(id) ? "Book has been deleted" : "Book hasn't been deleted";
    }


}
