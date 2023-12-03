package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface {@code CRDDao} describes CRD operations for working with database tables.
 *
 * @param <T> the type parameter
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface CRDDao<T> {


    T findById(long id) throws DaoException;


    List<T> findAll() throws DaoException;

    void save(T item) throws DaoException;


    void deleteById(long id) throws DaoException;

}
