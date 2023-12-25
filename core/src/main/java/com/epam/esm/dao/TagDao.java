package com.epam.esm.dao;

import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao extends CRDDao<Tag> {
    Optional<Tag> findByName(String name);

    List<Tag> findMostUsedTagListByUserId(Long userId, PageRequest pageRequest);
}
