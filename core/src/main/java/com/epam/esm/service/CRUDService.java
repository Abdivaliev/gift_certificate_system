package com.epam.esm.service;

public interface CRUDService<D> extends CRDService<D> {
    D update(long id, D dto);
}
