package com.epam.esm.repo.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.convertor.extractor.FieldExtractor;
import com.epam.esm.repo.AbstractDao;
import com.epam.esm.repo.CRUDDao;
import com.epam.esm.repo.TagRepo;
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
import static com.epam.esm.constants.FilterParameters.*;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.*;

/**
 * Class {@code GiftCertificateDaoImpl} is implementation of interface {@link CRUDDao}
 * and intended to work with 'gift_certificates' table.
 */

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements CRUDDao<GiftCertificate> {
    private static final String SELECT_JOINER = " gc LEFT JOIN gift_certificates_tags gct ON " +
            "gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";
    private static final String INSERT_QUERY = "INSERT INTO gift_certificates(name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    private final TagRepo tagRepo;
    private final FieldExtractor<GiftCertificate> giftCertificateFieldExtractor;
    private final QueryWriter queryWriter;

    @Autowired
    public GiftCertificateDaoImpl(QueryWriter queryWriter, JdbcTemplate jdbcTemplate, ResultSetExtractor<List<GiftCertificate>> rowMapper, TagRepo tagRepo,
                                  FieldExtractor<GiftCertificate> giftCertificateFieldExtractor) {
        super(jdbcTemplate, rowMapper);
        this.tagRepo = tagRepo;
        this.giftCertificateFieldExtractor = giftCertificateFieldExtractor;
        this.queryWriter = queryWriter;
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
            updateTags(giftCertificate, (int) keyHolder.getKeyList().get(0).get(ID));
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) throws DaoException {
        try {
            Map<String, String> fields = giftCertificateFieldExtractor.extract(giftCertificate);
            String updateQuery = queryWriter.updateQueryWriter(fields);
            executeUpdateQuery(updateQuery);
            updateTags(giftCertificate, giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    public List<GiftCertificate> findWithFilters(Map<String, String> fields) throws DaoException {
        try {
            String query = queryWriter.getWithParamQueryWriter(fields);
            return jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_PARAMETERS);
        }
    }

    @Override
    protected String getTableName() {
        return GIFT_CERTIFICATES;
    }

    @Override
    protected String getSelectJoiner() {
        return SELECT_JOINER;
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
            Tag tagWithId = tagRepo.findByName(tagName);
            tagIds.add(tagWithId.getId());
        }
        return tagIds;
    }

}
