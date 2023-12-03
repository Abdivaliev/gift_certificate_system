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
import java.util.Map;

import static com.epam.esm.entity.ColumnNames.TAGS;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.NO_ENTITY_WITH_NAME;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.SAVING_ERROR;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagRepo {
    private static final String INSERT_QUERY = "INSERT INTO tags(tag_name) values(?)";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE tag_name=?";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<Tag>> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    protected String getTableName() {
        return TAGS;
    }

    @Override
    protected String getSelectJoiner() {
        return "";
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
    public List<Tag> findByName(String name) throws DaoException {
        try {
            return executeQuery(GET_BY_NAME_QUERY, name);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_NAME);
        }
    }

}
