package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;
/**
 * Interface {@code CRUDDao} describes CRUD operations for working with database tables.
 *
 * @param <T> the type parameter
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */

public interface CRUDDao<T> extends CRDDao<T> {

    void update(T item) throws DaoException;
}
