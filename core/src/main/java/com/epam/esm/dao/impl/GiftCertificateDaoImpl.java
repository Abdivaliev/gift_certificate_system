package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.convertor.extractor.FieldExtractor;
import com.epam.esm.dao.CRUDDao;
import com.epam.esm.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

import static com.epam.esm.constants.ColumnNames.*;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.*;

/**
 * Class {@code GiftCertificateDaoImpl} is implementation of interface {@link CRUDDao}
 * and intended to work with 'gift_certificates' table.
 */

@Repository
public class GiftCertificateDaoImpl implements CRUDDao<GiftCertificate> {
    private static final String FIND_ALL_QUERY = "SELECT gc.*, json_agg(ROW(t.id,t.name)) AS tags\n" +
            "FROM gift_certificates gc\n" +
            "         LEFT JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id\n" +
            "         LEFT JOIN tags t ON gct.tag_id = t.id\n" +
            "GROUP BY gc.id;\n";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";
    private static final String DELETE_QUERY = "DELETE FROM " + GIFT_CERTIFICATES + " WHERE id=?";
    private static final String DELETE_RELATIONAL_QUERY = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " + GIFT_CERTIFICATES + "gc LEFT JOIN gift_certificates_tags gct ON \" +\n" +
            "            \"gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id WHERE gc.id=?";
    private static final String INSERT_QUERY = "INSERT INTO gift_certificates(name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    private final TagDao tagDao;
    private final FieldExtractor<GiftCertificate> giftCertificateFieldExtractor;
    private final QueryWriter queryWriter;
    private final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<GiftCertificate>> rowMapper;

    @Autowired
    public GiftCertificateDaoImpl(QueryWriter queryWriter, JdbcTemplate jdbcTemplate, TagDao tagDao,
                                  FieldExtractor<GiftCertificate> giftCertificateFieldExtractor, ResultSetExtractor<List<GiftCertificate>> rowMapper) {
        this.tagDao = tagDao;
        this.giftCertificateFieldExtractor = giftCertificateFieldExtractor;
        this.queryWriter = queryWriter;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<GiftCertificate> findAll() throws DaoException {
        try {
            return executeQuery(FIND_ALL_QUERY);
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED,e.getRootCause());
        }
    }

    @Override
    public GiftCertificate findById(long id) throws DaoException {
        try {
            return executeQueryAsSingleResult(FIND_BY_ID_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED, e.getRootCause());
        }
    }

    @Transactional
    @Override
    public void save(GiftCertificate giftCertificate) throws DaoException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setBigDecimal(3, giftCertificate.getPrice());
                ps.setInt(4, giftCertificate.getDuration());
                ps.setString(5, giftCertificate.getCreatedDate());
                ps.setString(6, giftCertificate.getUpdatedDate());
                return ps;
            }, keyHolder);
            updateTags(giftCertificate, (Long) keyHolder.getKeyList().stream().findFirst().get().get(ID));
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED, e.getRootCause());
        }
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) throws DaoException {
        try {
            Map<String, String> fields = giftCertificateFieldExtractor.extract(giftCertificate);
            String updateQuery = queryWriter.writeUpdateQuery(fields);
            executeUpdateQuery(updateQuery);
            updateTags(giftCertificate, giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED, e.getRootCause());
        }
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_RELATIONAL_QUERY, id);
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<GiftCertificate> findWithFilters(Map<String, String> fields) throws DaoException {
        try {
            String query = queryWriter.writeGetQueryWithParam(fields);
            return jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(ACCESS_TO_RESOURCE_LIMITED, e.getRootCause());
        }
    }


    private void updateTags(GiftCertificate giftCertificate, long giftCertificateId) throws DaoException {
        if (giftCertificate.getTags() != null) {
            getTagsIds(giftCertificate.getTags())
                    .forEach(tag_id -> executeUpdateQuery(ADD_TAGS_QUERY, giftCertificateId, tag_id));
        }
    }

    private List<Long> getTagsIds(List<Tag> tags) throws DaoException {
        List<Long> tagIds = new ArrayList<>();
        for (Tag tag : tags) {
            String tagName = tag.getName();
            Tag tagWithId = tagDao.findByName(tagName);
            tagIds.add(tagWithId.getId());
        }
        return tagIds;
    }

    private List<GiftCertificate> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    private GiftCertificate executeQueryAsSingleResult(String query, Object... params) {
        List<GiftCertificate> result = executeQuery(query, params);
        return result.stream().findFirst().orElse(null);
    }

    private void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }
}
