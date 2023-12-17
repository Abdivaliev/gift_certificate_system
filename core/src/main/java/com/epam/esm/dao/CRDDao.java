package com.epam.esm.dao;

import com.epam.esm.dto.PageRequest;

import java.util.List;
import java.util.Optional;

public interface CRDDao<T> {
    Optional<T> findById(long id) ;

    List<T> findAll(PageRequest pageableDTo) ;
    T save(T item) ;
    void deleteById(long id) ;

}
