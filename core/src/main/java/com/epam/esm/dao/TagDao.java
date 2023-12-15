package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

/**
 * Interface {@code TagRepo} describes abstract behavior for working with tags table in database.
 */
public interface TagDao extends CRDDao<Tag> {
    Tag findByName(String name) throws DaoException;
}
