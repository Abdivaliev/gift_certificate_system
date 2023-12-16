package com.epam.esm.dao;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CRDDao<T> {
    Optional<T> findById(long id) ;

    List<T> findAll(Pageable pageable) ;
    T save(T item) ;
    void deleteById(long id) ;

}
