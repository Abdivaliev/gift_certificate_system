package com.epam.esm.repo;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

/**
 * Interface {@code TagRepo} describes abstract behavior for working with tags table in database.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface TagRepo extends CRDDao<Tag> {
    List<Tag> findByName(String name) throws DaoException;
}
