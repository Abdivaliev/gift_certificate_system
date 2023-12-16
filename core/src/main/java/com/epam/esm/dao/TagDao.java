package com.epam.esm.dao;

import com.epam.esm.entity.Tag;


import java.util.Optional;

/**
 * Interface {@code TagRepo} describes abstract behavior for working with tags table in database.
 */
public interface TagDao extends CRDDao<Tag> {
    Optional<Tag> findByName(String name) ;
    Optional<Tag> findMostUsedTag();
}
