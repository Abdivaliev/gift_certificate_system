package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.util.List;
import java.util.Optional;
/**
 * Interface {@code CRDService} describes CRD operations for working with objects.
 *
 * @param <T> the type parameter
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface CRDService<T> {

    T findById(long id) throws DaoException, IncorrectParameterException;


    List<T> findAll() throws DaoException;


    void save(T entity) throws DaoException, IncorrectParameterException;


    void deleteById(long id) throws DaoException, IncorrectParameterException;
}
