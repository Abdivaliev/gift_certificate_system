package com.epam.esm.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<T> entityType;

    public AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    public List<T> findAll(Pageable pageable) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e", entityType)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public void deleteById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findByName(String name) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() +
                        " e WHERE e.name = :name", entityType)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }
}