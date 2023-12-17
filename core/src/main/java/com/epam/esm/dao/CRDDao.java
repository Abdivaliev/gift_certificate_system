package com.epam.esm.dao;

import com.epam.esm.dto.Pageable;

import java.util.List;
import java.util.Optional;

public interface CRDDao<T> {
    Optional<T> findById(long id) ;

    List<T> findAll(Pageable pageable) ;
    T save(T item) ;
    void deleteById(long id) ;

}
