package com.epam.esm.repo;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

public interface TagRepo extends CRDDao<Tag> {
    List<Tag> findByName(String name) throws DaoException;
}
