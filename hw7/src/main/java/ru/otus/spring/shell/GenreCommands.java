package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.utils.GenrePrinter;

@ShellComponent
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GenreCommands {


    private final GenreService genreService;

    private final GenrePrinter genrePrinter;

    @ShellMethod(value = "Create new genre", key = {"createGenre"})
    public void create(String name) {
        Genre genre = genreService.save(new Genre(name));
        genrePrinter.print("Genre has been created: ", genre);
    }

    @ShellMethod(value = "Update genre by id", key = {"updateGenre"})
    public String update(long id, String name) {
        Genre genre = new Genre(id, name);
        genreService.save(genre);
        return "Genre has been updated";
    }

    @ShellMethod(value = "Get genre by id", key = {"getGenre"})
    public void get(long id) {
        Genre genre = genreService.getById(id);
        genrePrinter.print(genre);
    }

    @ShellMethod(value = "Show all genres", key = {"allGenres"})
    public void all() {
        genrePrinter.print(genreService.getAll());
    }


    @ShellMethod(value = "Delete genre by id", key = {"deleteGenre"})
    public String delete(long id) {
        genreService.deleteById(id);
        return "Genre has been deleted";
    }


}
