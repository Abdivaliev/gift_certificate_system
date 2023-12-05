package com.epam.esm.repo.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.AbstractDao;
import com.epam.esm.repo.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.constants.ColumnNames.TAGS;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.NO_ENTITY_WITH_NAME;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.SAVING_ERROR;

/**
 * Class {@code TagDaoImpl} is implementation of interface {@link TagRepo} and intended to work with 'tags' table.
 */
@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagRepo {
    private static final String INSERT_QUERY = "INSERT INTO tags(tag_name) values(?)";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE tag_name=?";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<Tag>> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Transactional
    @Override
    public void save(Tag tag) throws DaoException {
        try {
            executeUpdateQuery(INSERT_QUERY, tag.getName());
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public Tag findByName(String name) throws DaoException {
        try {
            return executeQuery(GET_BY_NAME_QUERY, name).stream().findFirst().get();
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_NAME);
        }
    }
    @Override
    protected String getTableName() {
        return TAGS;
    }

    @Override
    protected String getSelectJoiner() {
        return "";
    }
}
