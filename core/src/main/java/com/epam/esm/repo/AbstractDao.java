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


public abstract class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<T>> rowMapper;

    protected abstract String getTableName();
    protected abstract String getSelectJoiner();


    public AbstractDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<T>> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    protected List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    protected T executeQueryAsSingleResult(String query, Object... params) {
        List<T> result = executeQuery(query, params);
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    protected void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    public T findById(long id) throws DaoException {
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM " + getTableName() +getSelectJoiner()+ " WHERE ");
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
            if (getTableName().equals(TAGS)) {
                executeUpdateQuery("DELETE FROM gift_certificates_tags WHERE tag_id=?", id);
            } else {
                executeUpdateQuery("DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?", id);
            }
            executeUpdateQuery("DELETE FROM " + getTableName() + " WHERE id=?", id);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }


}
