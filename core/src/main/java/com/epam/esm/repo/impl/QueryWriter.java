package com.epam.esm.repo.impl;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.constants.ColumnNames.*;
import static com.epam.esm.constants.FilterParameters.*;
import static com.epam.esm.constants.FilterParameters.SORT_BY_CREATE_DATE;

@Component
public class QueryWriter {

    private static final String SELECT_QUERY_WITH_PARAM = "SELECT * FROM " + GIFT_CERTIFICATES + " gc LEFT JOIN gift_certificates_tags gct ON gc.id=gct.gift_certificate_id LEFT JOIN " + TAGS + " t ON gct.tag_id=t.id";

    protected String writeUpdateQuery(Map<String, String> fields) {
        String updates = fields.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getKey().equals(ID))
                .map(entry -> entry.getKey() + "=" + '\'' + entry.getValue() + '\'')
                .collect(Collectors.joining(","));

        return "UPDATE " + GIFT_CERTIFICATES + " SET " + updates + " WHERE id=" + fields.get(ID);
    }

    protected String writeUpdateQueryWithParam(Map<String, String> fields) {
        StringBuilder query = new StringBuilder(SELECT_QUERY_WITH_PARAM);

        if (fields.get(TAG_NAME) != null) {
            addParameter(query, TAG_NAME, fields.get(TAG_NAME));
        }
        if (fields.get(NAME) != null) {
            addParameter(query, NAME, fields.get(NAME));
        }
        if (fields.get(PART_OF_NAME) != null) {
            addPartParameter(query, NAME, fields.get(PART_OF_NAME));
        }
        if (fields.get(PART_OF_DESCRIPTION) != null) {
            addPartParameter(query, DESCRIPTION, fields.get(PART_OF_DESCRIPTION));
        }
        if (fields.get(SORT_BY_NAME) != null) {
            addSortParameter(query, NAME, fields.get(SORT_BY_NAME));
        }
        if (fields.get(SORT_BY_CREATE_DATE) != null) {
            addSortParameter(query, CREATE_DATE, fields.get(SORT_BY_CREATE_DATE));
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
}
