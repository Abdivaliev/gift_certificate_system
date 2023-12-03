package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;


public interface CRUDService<T> extends CRDService<T> {

    void update(long id, T entity) throws DaoException, IncorrectParameterException;
}
