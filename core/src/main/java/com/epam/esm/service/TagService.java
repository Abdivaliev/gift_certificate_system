package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService extends CRDService<TagDto> {
    List<TagDto> findMostUsedTagListByUserId(Long userId,int page,int size);
}
