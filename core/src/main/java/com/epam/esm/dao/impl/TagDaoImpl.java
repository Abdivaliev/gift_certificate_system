package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.constants.ColumnNames.TAGS;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.*;

/**
 * Class {@code TagDaoImpl} is implementation of interface {@link TagDao} and intended to work with 'tags' table.
 */
@Repository
public class TagDaoImpl implements TagDao {
    private static final String INSERT_QUERY = "INSERT INTO tags(tag_name) values(?)";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE tag_name=?";
    private static final String DELETE_RELATION_QUERY = "DELETE FROM gift_certificates_tags WHERE tag_id=?";
    private static final String DELETE_QUERY = "DELETE FROM " + TAGS + " WHERE id=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " + TAGS + " WHERE id=? ";
    private static final String FIND_ALL_QUERY = "SELECT * FROM " + TAGS;
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<Tag>> rowMapper;
    @Override
    public List<Tag> findAll() throws DaoException {
        try {
            return executeQuery(FIND_ALL_QUERY);
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED,e.getRootCause());
        }
    }

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<Tag>> rowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Transactional
    @Override
    public void save(Tag tag) throws DaoException {
        try {
            executeUpdateQuery(INSERT_QUERY, tag.getName());
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED,e.getCause());
        }
    }

    @Override
    public Tag findByName(String name) throws DaoException {
        try {
            return executeQuery(GET_BY_NAME_QUERY, name).stream().findFirst().get();
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED,e.getCause());
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        executeUpdateQuery(DELETE_RELATION_QUERY, id);
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public Tag findById(long id) throws DaoException {
        try {
            return executeQueryAsSingleResult(FIND_BY_ID_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED,e.getCause());
        }
    }
    private List<Tag> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    private Tag executeQueryAsSingleResult(String query, Object... params) {
        List<Tag> result = executeQuery(query, params);
        return result.stream().findFirst().orElse(null);
    }

    private void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

}
