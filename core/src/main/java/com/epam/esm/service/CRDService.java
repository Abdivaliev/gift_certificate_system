package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;

import java.util.List;

/**
 * Interface {@code CRDService} describes CRD operations for working with objects.
 */
public interface CRDService<T> {

    T findById(long id) throws DaoException;


    List<T> findAll() throws DaoException;


    void save(T entity) throws DaoException;


    void deleteById(long id) throws DaoException;
}
