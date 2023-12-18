package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;


@Component
public class TagConverter implements Converter<Tag, TagDto> {

    @Override
    public Tag convertToEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());

        return tag;
    }

    @Override
    public TagDto convertToDto(Tag entity) {
        TagDto tagDto = new TagDto();

        tagDto.setId(entity.getId());
        tagDto.setName(entity.getName());
        tagDto.setCreatedDate(entity.getCreatedDate());
        tagDto.setUpdatedDate(entity.getUpdatedDate());

        return tagDto;
    }
}
