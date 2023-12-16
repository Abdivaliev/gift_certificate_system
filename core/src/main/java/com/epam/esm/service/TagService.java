package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

public interface TagService extends CRDService<TagDto> {
    TagDto findMostUsedTag();
}
