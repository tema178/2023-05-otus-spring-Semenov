package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.InputService;
import ru.otus.spring.utils.GenrePrinter;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GenreCommands {


    private final GenreDao genreDao;

    private final InputService inputService;
    
    private final GenrePrinter genrePrinter;

    @ShellMethod(value = "Create new genre", key = {"createGenre"})
    public void create(String name) {
        Genre genre = genreDao.create(new Genre(name));
        genrePrinter.print("Genre has been created: ", genre);
    }

    @ShellMethod(value = "Update genre by id", key = {"updateGenre"})
    public String update(long id) {
        String name = inputService.readStringWithPrompt("Enter genre name:");
        Genre genre = new Genre(id, name);
        return genreDao.update(genre) > 0 ? "Genre has been updated" : "Genre hasn't been updated";
    }

    @ShellMethod(value = "Get genre by id", key = {"getGenre"})
    public void get(long id) {
        Genre genre = genreDao.getById(id);
        genrePrinter.print(genre);
    }

    @ShellMethod(value = "Show all genres", key = {"allGenres"})
    public void all() {
        genrePrinter.print(genreDao.getAll());
    }


    @ShellMethod(value = "Delete genre by id", key = {"deleteGenre"})
    public String delete(long id) {
        return genreDao.deleteById(id) > 0 ? "Genre has been deleted" : "Genre hasn't been deleted";
    }


}
