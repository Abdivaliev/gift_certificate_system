package com.epam.esm.dao;

import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;

/**
 * Interface {@code CRUDDao} describes CRUD operations for working with database tables.
 */

public interface CRUDDao<T> extends CRDDao<T> {

    void update(T item) throws DaoException;

    List<T> findWithFilters(Map<String, String> fields) throws DaoException;

}
