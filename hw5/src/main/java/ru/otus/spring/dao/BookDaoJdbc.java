package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDaoJdbc implements BookDao {

    public static final String AUTHOR_ID_FIELD = "author_id";

    public static final String GENRE_ID_FIELD = "genre_id";

    public static final String ID_FIELD = "id";

    public static final String NAME_FIELD = "name";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book create(Book book) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue(AUTHOR_ID_FIELD, book.getAuthor().getId());
        params.addValue(GENRE_ID_FIELD, book.getGenre().getId());
        namedParameterJdbcOperations.update(
                "insert into books (name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, kh);
        book.setId(Objects.requireNonNull(kh.getKey()).longValue());
        return book;
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from books where id = :id", params, new BookDaoJdbc.BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "select * from books", new BookDaoJdbc.BookMapper()
        );
    }

    @Override
    public int update(Book book) {
        return namedParameterJdbcOperations.update("UPDATE books SET name = :name, author_id = :author_id, " +
                        "genre_id = :genre_id WHERE id = :id",
                Map.of(ID_FIELD, book.getId(), NAME_FIELD, book.getName(),
                        AUTHOR_ID_FIELD, book.getAuthor().getId(), GENRE_ID_FIELD, book.getGenre().getId()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong(ID_FIELD);
            String name = resultSet.getString(NAME_FIELD);
            long authorId = resultSet.getLong(AUTHOR_ID_FIELD);
            long genreId = resultSet.getLong(GENRE_ID_FIELD);
            Author author = new Author(authorId, null);
            Genre genre = new Genre(genreId, null);
            return new Book(id, name, author, genre);
        }
    }

}
