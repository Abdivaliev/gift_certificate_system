package com.epam.esm.repo;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

/**
 * Interface {@code TagRepo} describes abstract behavior for working with tags table in database.
 */
public interface TagRepo extends CRDDao<Tag> {
    Tag findByName(String name) throws DaoException;
}
