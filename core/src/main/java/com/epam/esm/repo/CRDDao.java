package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface CRDDao<T> {


    T findById(long id) throws DaoException;


    List<T> findAll() throws DaoException;

    void save(T item) throws DaoException;


    void deleteById(long id) throws DaoException;

}
