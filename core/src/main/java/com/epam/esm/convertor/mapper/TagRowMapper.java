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
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Component
public class TagRowMapper implements ResultSetExtractor<List<Tag>> {
    /**
     * Extracts data from a ResultSet and maps it to a list of Tag objects.
     * It iterates over the ResultSet and for each row, it creates a Tag object,
     * sets its fields with the data from the row, and adds it to the list.
     *
     * @param rs The ResultSet to extract data from.
     * @return A list of Tag objects with the data extracted from the ResultSet.
     * @throws SQLException        If there is an error with the SQL operation.
     * @throws DataAccessException If there is an error with the data access operation.
     */
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
