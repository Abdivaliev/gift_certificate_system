package com.epam.esm.convertor.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constants.ColumnNames.*;


@Component
public class TagRowMapper implements ResultSetExtractor<List<Tag>> {

    @Override
    public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Tag> tags = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            Tag tag = new Tag();
            tag.setId(rs.getLong(ID));
            tag.setName(rs.getString(TAG_NAME));
            tags.add(tag);
            rs.next();
        }
        return tags;
    }
}
