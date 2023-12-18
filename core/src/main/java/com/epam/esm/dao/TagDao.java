package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao extends CRDDao<Tag> {
    Optional<Tag> findByName(String name);

    Map<List<?>, List<?>> findMostUsedTagByUserId(Long userId);
}
