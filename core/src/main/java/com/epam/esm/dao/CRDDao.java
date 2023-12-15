package com.epam.esm.dao;

import com.epam.esm.exceptions.DaoException;

import java.util.List;

/**
 * Interface {@code CRDDao} describes CRD operations for working with database tables.
 */
public interface CRDDao<T> {


    T findById(long id) throws DaoException;


    List<T> findAll() throws DaoException;

    void save(T item) throws DaoException;


    void deleteById(long id) throws DaoException;

}
