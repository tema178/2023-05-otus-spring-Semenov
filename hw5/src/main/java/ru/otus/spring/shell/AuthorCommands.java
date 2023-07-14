package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.InputService;
import ru.otus.spring.utils.AuthorPrinter;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthorCommands {


    private final AuthorDao authorDao;

    private final InputService inputService;

    private final AuthorPrinter authorPrinter;

    @ShellMethod(value = "Create new author", key = {"createAuthor"})
    public void create(String name) {
        Author author = authorDao.create(new Author(name));
        authorPrinter.print("Author has been created: ", author);
    }

    @ShellMethod(value = "Update author by id", key = {"updateAuthor"})
    public String update(long id) {
        String name = inputService.readStringWithPrompt("Enter author name:");
        Author author = new Author(id, name);
        return authorDao.update(author) > 0 ? "Author has been updated" : "Author hasn't been updated";
    }

    @ShellMethod(value = "Get author by id", key = {"getAuthor"})
    public void get(long id) {
        Author author = authorDao.getById(id);
        authorPrinter.print(author);
    }

    @ShellMethod(value = "Show all authors", key = {"allAuthors"})
    public void all() {
        authorPrinter.print(authorDao.getAll());
    }

    @ShellMethod(value = "Delete author by id", key = {"deleteAuthor"})
    public String delete(long id) {
        return authorDao.deleteById(id) > 0 ? "Author has been deleted" : "Author hasn't been deleted";
    }


}
