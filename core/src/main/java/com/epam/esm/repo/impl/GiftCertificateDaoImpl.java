package com.epam.esm.repo.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.convertor.extractor.FieldExtractor;
import com.epam.esm.repo.AbstractDao;
import com.epam.esm.repo.GiftCertificateRepo;
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
import java.util.stream.Collectors;

import static com.epam.esm.constants.ColumnNames.GIFT_CERTIFICATES;
import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.*;


@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateRepo {
    private final TagRepo tagRepo;
    private final FieldExtractor<GiftCertificate> giftCertificateFieldExtractor;
    private static final String SELECT_JOINER = " gc LEFT JOIN gift_certificates_tags gct ON " +
            "gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id";
    private static final String ADD_TAGS_QUERY = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";
    private static final String INSERT_QUERY = "INSERT INTO gift_certificates(name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<GiftCertificate>> rowMapper, TagRepo tagRepo,
                                  FieldExtractor<GiftCertificate> giftCertificateFieldExtractor) {
        super(jdbcTemplate, rowMapper);
        this.tagRepo = tagRepo;
        this.giftCertificateFieldExtractor = giftCertificateFieldExtractor;
    }

    @Override
    protected String getTableName() {
        return GIFT_CERTIFICATES;
    }

    @Override
    protected String getSelectJoiner() {
        return SELECT_JOINER;
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
            updateTags(giftCertificate, (int) keyHolder.getKeyList().get(0).get("id"));
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) throws DaoException {
        try {
            Map<String, String> fields = giftCertificateFieldExtractor.extract(giftCertificate);
            String updateQuery = updateQueryWriter(fields);
            executeUpdateQuery(updateQuery);
            updateTags(giftCertificate, giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    public List<GiftCertificate> findWithFilters(Map<String, String> fields) throws DaoException {
        try {
            String query = getWithParamQueryWriter(fields);
            return jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_PARAMETERS);
        }
    }


    private String updateQueryWriter(Map<String, String> fields) {
        String updates = fields.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getKey().equals("id"))
                .map(entry -> entry.getKey() + "=" + '\'' + entry.getValue() + '\'')
                .collect(Collectors.joining(","));

        return "UPDATE " + GIFT_CERTIFICATES + " SET " + updates + " WHERE id=" + fields.get("id");
    }

    public String getWithParamQueryWriter(Map<String, String> fields) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + GIFT_CERTIFICATES + " gc LEFT JOIN gift_certificates_tags gct ON gc.id=gct.gift_certificate_id LEFT JOIN tags t ON gct.tag_id=t.id");

        if (fields.get("tag_name") != null) {
            addParameter(query, "tag_name", fields.get("tag_name"));
        }
        if (fields.get("name") != null) {
            addParameter(query, "name", fields.get("name"));
        }
        if (fields.get("partOfName") != null) {
            addPartParameter(query, "name", fields.get("partOfName"));
        }
        if (fields.get("partOfDescription") != null) {
            addPartParameter(query, "description", fields.get("partOfDescription"));
        }
        if (fields.get("sortByName") != null) {
            addSortParameter(query, "name", fields.get("sortByName"));
        }
        if (fields.get("sortByCreateDate") != null) {
            addSortParameter(query, "create_date", fields.get("sortByCreateDate"));
        }

        return query.toString();
    }

    private void addParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append("='").append(value).append('\'');
    }

    private void addPartParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append(" LIKE '%").append(value).append("%'");
    }

    private void addSortParameter(StringBuilder query, String sortParameter, String value) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(sortParameter).append(" ").append(value);
    }

    private void updateTags(GiftCertificate giftCertificate, long giftCertificateId) {
        if (giftCertificate.getTags() != null) {
            getTagsIds(giftCertificate.getTags())
                    .forEach(tag_id -> executeUpdateQuery(ADD_TAGS_QUERY, giftCertificateId, tag_id));
        }
    }

    private List<Long> getTagsIds(List<Tag> tags) {
        List<Long> tagIds = new ArrayList<>();
        tags.forEach(tag -> {
            String tagName = tag.getName();
            Tag tagWithId = null;
            try {
                tagWithId = tagRepo.findByName(tagName).get(0);
            } catch (DaoException e) {
                e.printStackTrace();
            }
            tagIds.add(tagWithId.getId());
        });
        return tagIds;
    }
}
