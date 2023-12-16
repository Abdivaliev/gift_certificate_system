package com.epam.esm.service;


import java.util.List;

public interface CRDService<D> {
    D findById(long id);


    List<D> findAll(int page, int size);


    D save(D dto);


    void deleteById(long id);
}
