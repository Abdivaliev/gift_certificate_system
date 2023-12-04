package com.epam.esm.service;


import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.CRDDao;


import java.util.List;

/**
 * Class {@code AbstractService} is implementation of interface {@link CRDService} and is designed for basic work with objects.
 *
 * @param <T> the type parameter
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public abstract class AbstractService<T> implements CRDService<T> {
    protected final CRDDao<T> dao;

    public AbstractService(CRDDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T findById(long id) throws DaoException {

        return dao.findById(id);
    }

    @Override
    public List<T> findAll() throws DaoException {
        return dao.findAll();
    }

    @Override
    public void deleteById(long id) throws DaoException {

        dao.deleteById(id);
    }


}
