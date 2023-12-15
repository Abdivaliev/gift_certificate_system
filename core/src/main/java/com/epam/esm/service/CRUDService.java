package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.DaoException;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Interface {@code CRUDService} describes CRUD operations for working with objects.
 *
 */
public interface CRUDService<T> extends CRDService<T> {

    void update(long id, T entity) throws DaoException;
    List<T> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;

}
