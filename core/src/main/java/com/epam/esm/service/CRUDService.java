package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

/**
 * Interface {@code CRUDService} describes CRUD operations for working with objects.
 *
 * @param <T> the type parameter
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface CRUDService<T> extends CRDService<T> {

    void update(long id, T entity) throws DaoException, IncorrectParameterException;
}
