package com.epam.esm.dao;


/**
 * Interface {@code CRUDDao} describes CRUD operations for working with database tables.
 */

public interface CRUDDao<T> extends CRDDao<T> {
    T update(T entity);
}
