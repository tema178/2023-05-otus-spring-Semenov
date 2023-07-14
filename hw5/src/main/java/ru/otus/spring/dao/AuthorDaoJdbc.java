package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private static final String ID_FIELD = "id";

    private static final String NAME_FIELD = "name";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public Author create(Author author) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME_FIELD, author.getName());
        namedParameterJdbcOperations.update("insert into authors (name) values (:name)", params, kh);
        author.setId(Objects.requireNonNull(kh.getKey()).longValue());
        return author;
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from authors where id = :id", params, new AuthorDaoJdbc.AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "select * from authors", new AuthorDaoJdbc.AuthorMapper()
        );
    }

    @Override
    public int update(Author author) {
        return namedParameterJdbcOperations.update("UPDATE authors SET name = :name WHERE id = :id",
                Map.of(ID_FIELD, author.getId(), NAME_FIELD, author.getName()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong(ID_FIELD);
            String name = resultSet.getString(NAME_FIELD);
            return new Author(id, name);
        }
    }

}
