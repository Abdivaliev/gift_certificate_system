package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MostUsedTagListConverter implements Converter<Tag, Tuple> {
    @Override
    public Tag convertToEntity(Tuple tuple) {
        Tag tag = new Tag();
        tag.setId(tuple.get("tagid", Long.class));
        tag.setName(tuple.get("tagname", String.class));
        tag.setCreatedDate(tuple.get("createddate", Timestamp.class).toLocalDateTime());

        if (tuple.get("updateddate", Timestamp.class) != null) {
            tag.setUpdatedDate(tuple.get("updateddate", Timestamp.class).toLocalDateTime());
        }

        return tag;
    }

    @Override
    public Tuple convertToDto(Tag entity) {
        throw new UnsupportedOperationException();
    }

}
