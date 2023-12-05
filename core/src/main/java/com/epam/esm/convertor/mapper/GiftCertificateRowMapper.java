package com.epam.esm.convertor.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constants.ColumnNames.*;

/**
 * Mapper class for mapping rows of a ResultSet to a GiftCertificate object.
 * This class implements the ResultSetExtractor interface and is used to extract data from a ResultSet.
 */
@Component
public class GiftCertificateRowMapper implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong(ID));
            giftCertificate.setName(rs.getString(NAME));
            giftCertificate.setDescription(rs.getString(DESCRIPTION));
            giftCertificate.setPrice(rs.getBigDecimal(PRICE));
            giftCertificate.setDuration(rs.getInt(DURATION));
            giftCertificate.setCreatedDate(rs.getTimestamp(CREATE_DATE));
            giftCertificate.setUpdatedDate(rs.getTimestamp(UPDATE_DATE));

            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getInt(ID) == giftCertificate.getId()) {
                long tagId = rs.getLong(TAG_ID);
                String tagName = rs.getString(TAG_NAME);
                if (tagId != 0 && tagName != null) {
                    tags.add(new Tag(tagId, tagName));
                }
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }

}
