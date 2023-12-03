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

@Component
public class GiftCertificateRowMapper implements ResultSetExtractor<List<GiftCertificate>> {

    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";

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
            giftCertificate.setCreatedDate(LocalDateTime.parse(rs.getString(CREATE_DATE)));
            giftCertificate.setUpdatedDate(LocalDateTime.parse(rs.getString(UPDATE_DATE)));

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
