package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.constants.ColumnNames.GIFT_CERTIFICATES;
import static com.epam.esm.constants.ColumnNames.TAGS;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.NO_ENTITY;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.NO_ENTITY_WITH_ID;

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

    public T findById(long id) throws DaoException {
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM " + getTableName() + getSelectJoiner() + " WHERE ");
            if ((getTableName().equals(GIFT_CERTIFICATES))) {
                query.append("gc.id=?");
            } else {
                query.append("id=?");
            }
            return executeQueryAsSingleResult(query.toString(), id);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    public List<T> findAll() throws DaoException {
        try {
            return executeQuery("SELECT * FROM " + getTableName() + getSelectJoiner());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }
    }

    @Transactional
    public void deleteById(long id) throws DaoException {
        try {
            String tableNameID = getTableName().substring(0, getTableName().length() - 1) + "_id";
            executeUpdateQuery("DELETE FROM gift_certificates_tags WHERE " + tableNameID + "=?", id);
            executeUpdateQuery("DELETE FROM " + getTableName() + " WHERE id=?", id);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    protected List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    protected T executeQueryAsSingleResult(String query, Object... params) {
        List<T> result = executeQuery(query, params);
        if (result.size() == 1) {
            return result.stream().findFirst().get();
        } else {
            return null;
        }
    }

    protected void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    protected abstract String getTableName();

    protected abstract String getSelectJoiner();

}
