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

/**
 * Mapper class for mapping rows of a ResultSet to a Tag object.
 * This class implements the ResultSetExtractor interface and is used to extract data from a ResultSet.
 */
@Component
public class TagRowMapper implements ResultSetExtractor<List<Tag>> {
    @Override
    public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Tag> tags = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            Tag tag = new Tag();
            tag.setId(rs.getLong(1));
            tag.setName(rs.getString(2));
            tags.add(tag);
            rs.next();
        }
        return tags;
    }
}
