package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.NO_ENTITY;

/**
 * Class {@code AbstractDao} is designed for basic work with database tables.
 */

public abstract class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<T>> rowMapper;

    public AbstractDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<T>> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public List<T> findAll() throws DaoException {
        try {
            return executeQuery("SELECT * FROM " + getTableName() + getSelectJoiner());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }
    }


    protected List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    protected T executeQueryAsSingleResult(String query, Object... params) {
        List<T> result = executeQuery(query, params);
        return result.stream().findFirst().orElse(null);
    }

    protected void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    protected abstract String getTableName();

    protected abstract String getSelectJoiner();

}
