package com.epam.esm.repo;

import com.epam.esm.exceptions.DaoException;


public interface CRUDDao<T> extends CRDDao<T> {

    void update(T item) throws DaoException;
}
