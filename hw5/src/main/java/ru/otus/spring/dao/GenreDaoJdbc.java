package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private static final String ID_FIELD = "id";

    private static final String NAME_FIELD = "name";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public Genre create(Genre genre) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME_FIELD, genre.getName());
        namedParameterJdbcOperations.update("insert into genres (name) values (:name)", params, kh);
        genre.setId(Objects.requireNonNull(kh.getKey()).longValue());
        return genre;
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from genres where id = :id", params, new GenreDaoJdbc.GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query(
                "select * from genres", new GenreDaoJdbc.GenreMapper()
        );
    }

    @Override
    public int update(Genre genre) {
        return namedParameterJdbcOperations.update("UPDATE genres SET name = :name WHERE id = :id",
                Map.of(NAME_FIELD, genre.getName(), ID_FIELD, genre.getId()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID_FIELD, id);
        return namedParameterJdbcOperations.update(
                "delete from genres where id = :id", params
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            String name = resultSet.getString(NAME_FIELD);
            long id = resultSet.getLong(ID_FIELD);
            return new Genre(id, name);
        }
    }

}
