package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.converter.Converter;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MostUsedTagConverter implements Converter<Tuple, MostUsedTagDto> {

    @Override
    public Tuple convertToEntity(MostUsedTagDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MostUsedTagDto convertToDto(Tuple tuple) {
        return new MostUsedTagDto(
                tuple.get("user_id", Long.class),
                tuple.get("tag_id", Long.class),
                tuple.get("tag_name", String.class),
                tuple.get("tag_count", Long.class),
                tuple.get("total_cost", BigDecimal.class));
    }
}
